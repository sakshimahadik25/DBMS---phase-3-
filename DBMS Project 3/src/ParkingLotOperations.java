import java.sql.*;

public class ParkingLotOperations {
    public static void ParkingLotChoice(int ParkingLotChoice, Connection DB) {
        switch (ParkingLotChoice) {
            case 1:
                EnterParkingLotInfo(DB);
                break;

            case 2:
                UpdateParkingLotInfo(DB);
                break;

            case 3:
                DeleteParkingLot(DB);
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void EnterParkingLotInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String ParkingLotName = UserInput.getString("\nEnter parking lot name");
        String Address = UserInput.getString("Enter parking lot's address");
        String query = "INSERT INTO ParkingLots (ParkingLotName, Address)" +
                "VALUES ('" + ParkingLotName + "', '" + Address + "')";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! New Parking Lot information successfully entered!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void UpdateParkingLotInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String ParkingLotName = UserInput.getString("\nEnter Parking Lot name you want to update");
        String Address = UserInput.getString("\nEnter the address");
        String query = "UPDATE ParkingLots SET Address = '" + Address + "' WHERE ParkingLotName = '" + ParkingLotName
                + "'";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Parking Lot information successfully updated!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void DeleteParkingLot(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String ParkingLotName = UserInput.getString("\nEnter Parking Lot name you want to delete");
        String query = "DELETE FROM ParkingLots WHERE ParkingLotName=" + "'" + ParkingLotName + "'";
        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Parking Lot successfully deleted!");
            }

            else {
                System.out.println("\nError while deleting Parking Lot!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
