import java.sql.*;
import java.util.Properties;

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

    static void close() {
        if (DB != null) {
            try {
                DB.close();
            } catch (Throwable error) {
                System.out.println("Error while closing database connection - " + error.getMessage());
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

    static void close(PreparedStatement st) {
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
