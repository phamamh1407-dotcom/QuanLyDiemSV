package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Diem;
import util.DBConnection;

/** Các thao tác với bảng Diem. */
public class DiemDAO {
    private static final String SELECT =
        "SELECT d.MaSV, s.HoTen, d.MaMH, m.TenMH, m.SoTinChi, d.DiemQT, d.DiemThi "
      + "FROM Diem d JOIN SinhVien s ON d.MaSV = s.MaSV JOIN MonHoc m ON d.MaMH = m.MaMH ";

    private List<Diem> doc(PreparedStatement ps) throws SQLException {
        List<Diem> ds = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ds.add(new Diem(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getDouble(6), rs.getDouble(7)));
            }
        }
        return ds;
    }

    /** Tìm theo mã SV, họ tên, mã môn hoặc tên môn. Từ khóa rỗng = lấy tất cả. */
    public List<Diem> timKiem(String tuKhoa) throws SQLException {
        String sql = SELECT + "WHERE d.MaSV LIKE ? OR s.HoTen LIKE ? OR d.MaMH LIKE ? OR m.TenMH LIKE ? ORDER BY d.MaSV, d.MaMH";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String k = "%" + tuKhoa.trim() + "%";
            ps.setString(1, k);
            ps.setNString(2, k);
            ps.setString(3, k);
            ps.setNString(4, k);
            return doc(ps);
        }
    }

    public List<Diem> layTheoSinhVien(String maSV) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT + "WHERE d.MaSV = ? ORDER BY d.MaMH")) {
            ps.setString(1, maSV);
            return doc(ps);
        }
    }

    public boolean tonTai(String maSV, String maMH) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM Diem WHERE MaSV = ? AND MaMH = ?")) {
            ps.setString(1, maSV);
            ps.setString(2, maMH);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void them(String maSV, String maMH, double qt, double thi) throws SQLException {
        String sql = "INSERT INTO Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maSV);
            ps.setString(2, maMH);
            ps.setDouble(3, qt);
            ps.setDouble(4, thi);
            ps.executeUpdate();
        }
    }

    public int sua(String maSV, String maMH, double qt, double thi) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE Diem SET DiemQT = ?, DiemThi = ? WHERE MaSV = ? AND MaMH = ?")) {
            ps.setDouble(1, qt);
            ps.setDouble(2, thi);
            ps.setString(3, maSV);
            ps.setString(4, maMH);
            return ps.executeUpdate();
        }
    }

    public int xoa(String maSV, String maMH) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Diem WHERE MaSV = ? AND MaMH = ?")) {
            ps.setString(1, maSV);
            ps.setString(2, maMH);
            return ps.executeUpdate();
        }
    }
}
