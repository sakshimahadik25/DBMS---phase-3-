import java.sql.*;
import java.util.Properties;

public class WolfParkingManagementSystem {
    public static void main (String args[]) {
        try {
            Connection connection = WolfParkingManagementSystem.databaseConnection();
            String query = "SELECT * FROM ParkingLots";
            try{
                Statement stmt = connection.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while(result.next()){
                    String parkingLotName = result.getString("ParkingLotName");
                    String address = result.getString("Address");
                    System.out.println(parkingLotName + " " + address);
                }
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        } catch(SQLException e) {
            System.out.println("Error while connecting to database - " + e.getMessage());
        }
    }

    private static Connection databaseConnection() throws SQLException {
        Connection connection = null;
        Properties props = new Properties();
        props.put("user", "");
        props.put("password", "");

        connection = DriverManager.getConnection("jdbc:mariadb://classdb2.csc.ncsu.edu:3306/sayitha", props);
        return connection;
    }
}
