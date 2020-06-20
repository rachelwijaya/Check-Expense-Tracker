package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    Connection conn = null;

    public static Connection connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/users", "root", "");
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}