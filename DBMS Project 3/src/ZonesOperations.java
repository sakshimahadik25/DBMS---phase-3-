import java.sql.*;

public class ZonesOperations {
    public static void ZonesChoice(int ZonesChoice, Connection DB) {
        switch (ZonesChoice) {
            case 1:
                AssignZone(DB);
                break;

            case 2:
                UpdateZoneInfo(DB);
                break;

            case 3:
                DeleteZoneInfo(DB);
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void AssignZone(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String ZoneID = UserInput.getString("\nEnter Zone ID");
        String ParkingLotName = UserInput.getString("Enter Parking Lot name");
        String query = "INSERT INTO Zones (ZoneID, ParkingLotName) VALUES ('" +
                ZoneID + "','" + ParkingLotName + "')";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Zone successfully assigned to Parking Lot!");
            }

            else {
                System.out.println("\nError while adding new Zone!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void UpdateZoneInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String ZoneID = UserInput.getString("\nEnter Zone ID you want to update");
        String ParkingLotName = UserInput.getString("Enter Parking Lot name you want to update");
        String newZoneID = UserInput.getString("\nEnter new Zone ID");
        String newParkingLotName = UserInput.getString("\nEnter new Parking Lot name");
        String query = "UPDATE Zones SET ParkingLotName = '" + newParkingLotName + "', " +
                "ZoneID = '" + newZoneID + "' WHERE ZoneID = '" + ZoneID + "' AND" +
                " ParkingLotName = '" + ParkingLotName + "'";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Zone information successfully updated!");
            }

            else {
                System.out.println("\nError while updating Zone Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void DeleteZoneInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String ZoneID = UserInput.getString("\nEnter Zone ID you want to delete");
        String ParkingLotName = UserInput.getString("Enter Parking Lot name you want to delete");
        String query = "DELETE FROM Zones WHERE ZoneID = '" + ZoneID + "'" +
                "AND ParkingLotName = '" + ParkingLotName + "'";
        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Zone information successfully deleted!");
            }

            else {
                System.out.println("\nError while deleting Zone Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
