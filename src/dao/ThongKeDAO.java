package dao;

import java.sql.*;
import util.DBConnection;

/** Đếm số bản ghi để hiển thị thống kê ở trang chủ. */
public class ThongKeDAO {
    public int demSinhVien() throws SQLException { return dem("SELECT COUNT(*) FROM SinhVien"); }
    public int demMonHoc() throws SQLException { return dem("SELECT COUNT(*) FROM MonHoc"); }
    public int demDiem() throws SQLException { return dem("SELECT COUNT(*) FROM Diem"); }

    private int dem(String sql) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
