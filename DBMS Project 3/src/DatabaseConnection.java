import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection DB = null;

    public static void connect() throws SQLException {
        Properties props = new Properties();
        props.put("user", "sayitha");
        props.put("password", "200498671");

        DB = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu:3306/sayitha", props);
    }

    public static Connection getDBInstance(){
        return DB;
    }
}
