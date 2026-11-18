package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MonHoc;
import util.DBConnection;

/** Đọc danh sách môn học. */
public class MonHocDAO {
    public List<MonHoc> layTatCa() throws SQLException {
        List<MonHoc> ds = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT MaMH, TenMH, SoTinChi FROM MonHoc ORDER BY MaMH");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(new MonHoc(rs.getString(1), rs.getString(2), rs.getInt(3)));
        }
        return ds;
    }
}
