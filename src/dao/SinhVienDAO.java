package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.SinhVien;
import util.DBConnection;

/** Các thao tác với bảng SinhVien (dùng PreparedStatement để tránh SQL Injection). */
public class SinhVienDAO {

    private SinhVien doc(ResultSet rs) throws SQLException {
        Date d = rs.getDate("NgaySinh");
        return new SinhVien(rs.getString("MaSV"), rs.getString("HoTen"),
                d == null ? null : d.toLocalDate(),
                rs.getString("GioiTinh"), rs.getString("DiaChi"), rs.getString("MaLop"));
    }

    /** Tìm theo mã SV, họ tên hoặc mã lớp. Từ khóa rỗng = lấy tất cả. */
    public List<SinhVien> timKiem(String tuKhoa) throws SQLException {
        String sql = "SELECT * FROM SinhVien WHERE MaSV LIKE ? OR HoTen LIKE ? OR MaLop LIKE ? ORDER BY MaSV";
        List<SinhVien> ds = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String k = "%" + tuKhoa.trim() + "%";
            ps.setString(1, k);
            ps.setNString(2, k);
            ps.setString(3, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ds.add(doc(rs));
            }
        }
        return ds;
    }

    public List<SinhVien> layTatCa() throws SQLException {
        return timKiem("");
    }

    public boolean tonTai(String maSV) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM SinhVien WHERE MaSV = ?")) {
            ps.setString(1, maSV);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public List<String> layDanhSachLop() throws SQLException {
        List<String> ds = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT MaLop FROM Lop ORDER BY MaLop");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(rs.getString(1));
        }
        return ds;
    }

    public void them(SinhVien sv) throws SQLException {
        String sql = "INSERT INTO SinhVien(MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSV());
            ps.setNString(2, sv.getHoTen());
            ps.setDate(3, Date.valueOf(sv.getNgaySinh()));
            ps.setNString(4, sv.getGioiTinh());
            ps.setNString(5, sv.getDiaChi());
            ps.setString(6, sv.getMaLop());
            ps.executeUpdate();
        }
    }

    public int sua(SinhVien sv) throws SQLException {
        String sql = "UPDATE SinhVien SET HoTen=?, NgaySinh=?, GioiTinh=?, DiaChi=?, MaLop=? WHERE MaSV=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setNString(1, sv.getHoTen());
            ps.setDate(2, Date.valueOf(sv.getNgaySinh()));
            ps.setNString(3, sv.getGioiTinh());
            ps.setNString(4, sv.getDiaChi());
            ps.setString(5, sv.getMaLop());
            ps.setString(6, sv.getMaSV());
            return ps.executeUpdate();
        }
    }

    /** Xóa sinh viên: xóa điểm của sinh viên trước (khóa ngoại), rồi xóa sinh viên, trong 1 giao dịch. */
    public int xoa(String maSV) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement("DELETE FROM Diem WHERE MaSV = ?");
                 PreparedStatement ps2 = c.prepareStatement("DELETE FROM SinhVien WHERE MaSV = ?")) {
                ps1.setString(1, maSV);
                ps1.executeUpdate();
                ps2.setString(1, maSV);
                int n = ps2.executeUpdate();
                c.commit();
                return n;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }
}
