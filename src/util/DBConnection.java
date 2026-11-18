package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Tạo kết nối JDBC tới SQL Server. Thông tin đọc từ file db.properties (không ghi cứng mật khẩu). */
public class DBConnection {

    public static Connection getConnection() throws SQLException {
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream("db.properties")) {
            p.load(in);
        } catch (IOException e) {
            throw new SQLException("Không đọc được file db.properties (hãy chạy chương trình từ thư mục gốc của project).");
        }
        String host = p.getProperty("host", "localhost").trim();
        String port = p.getProperty("port", "1433").trim();
        String db = p.getProperty("database", "QuanLyDiemSV").trim();
        String user = p.getProperty("user", "sa").trim();
        String pass = p.getProperty("password", "").trim();

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Thiếu JDBC Driver: hãy chép file mssql-jdbc-*.jar vào thư mục lib.");
        }
        String url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + db
                + ";encrypt=true;trustServerCertificate=true;loginTimeout=10";
        return DriverManager.getConnection(url, user, pass);
    }
}
