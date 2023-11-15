import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;

public class DatabaseConnection {
    private static Connection DB = null;

    public static void connect() throws SQLException {
        Properties props = new Properties();
        props.put("user", "sayitha");
        props.put("password", "200498671");

        DB = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu:3306/sayitha", props);
    }

    public static Connection getDBInstance() {
        return DB;
    }

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
