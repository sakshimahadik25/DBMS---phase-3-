import java.sql.*;

public class WolfParkingManagementSystem {
    public static void main (String args[]) {
        try {
            DatabaseConnection.connect();
            Connection DB = DatabaseConnection.getDBInstance();
            String query = "SELECT * FROM ParkingLots";
            try{
                Statement stmt = DB.createStatement();
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
}
