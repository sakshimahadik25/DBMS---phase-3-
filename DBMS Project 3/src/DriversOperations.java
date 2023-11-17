import java.sql.*;

public class DriversOperations {

    public static void DriversChoice(int DriverChoice, Connection DB) {
        switch (DriverChoice) {
            case 1:
                EnterDriverInfo(DB);
                break;

            case 2:
                UpdateDriverInfo(DB);
                break;

            case 3:
                DeleteDriverInfo(DB);
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void EnterDriverInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String DriverID = UserInput.getString("\nEnter driver's ID or Phone Number");
        String Name = UserInput.getString("Enter driver's name");
        String DriverStatus = UserInput.getString("Enter Driver Status:" +
                "\n1. If Student, enter S" +
                "\n2. If Employee, enter E" +
                "\n3. If Visitor, enter V" +
                "\nEnter");
        String query = "INSERT INTO Drivers(DriverID,Name,Status) " +
                "VALUES (" + "'" + DriverID + "'" + "," + "'" + Name + "'" + "," + "'" + DriverStatus + "'" + ")";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Driver information successfully entered!");
            }

            else {
                System.out.println("\nError while entering Driver Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void UpdateDriverInfo(Connection DB) {
        Statement stmt = null;
        String query = "";
        String deleteQuery1 = "";
        String deleteQuery2 = "";
        System.out.println("\n-----------------------------------------");
        String DriverID = UserInput.getString("\nEnter driver's ID or Phone Number you want to update");
        System.out.println("\nEnter the field you want to update:" +
                "\n1. Name \n2. Status");
        int updateChoice = UserInput.getInt("\nEnter your choice");
        if (updateChoice == 1) {
            String Name = UserInput.getString("Update driver's name");
            query = "UPDATE Drivers set Name=" + "'" + Name + "'" +
                    "WHERE DriverID=" + "'" + DriverID + "'";
        } else if (updateChoice == 2) {
            String DriverStatus = UserInput.getString("Update Driver Status:" +
                    "\n1. If Student, enter S" +
                    "\n2. If Employee, enter E" +
                    "\n3. If Visitor, enter V" +
                    "\nEnter");
            query = "UPDATE Drivers set Status=" + "'" + DriverStatus + "'" +
                    "WHERE DriverID=" + "'" + DriverID + "'";
            deleteQuery1 = "DELETE FROM Permits WHERE DriverID = " + DriverID;
            deleteQuery2 = "DELETE FROM Citations WHERE DriverID = " + DriverID;
        }

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Driver information successfully updated!");
            }

            else {
                System.out.println("\nError while updating Driver Information!");
            }

            // If Driver Status changes, all it's previous citations and permits are deleted.
            stmt.executeUpdate(deleteQuery1);
            stmt.executeUpdate(deleteQuery2);
        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void DeleteDriverInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String DriverID = UserInput.getString("\nEnter driver's ID or Phone Number you want to delete");
        String query = "DELETE FROM Drivers WHERE DriverID=" + "'" + DriverID + "'";
        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Driver information successfully deleted!");
            }

            else {
                System.out.println("\nError while deleting Driver Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
