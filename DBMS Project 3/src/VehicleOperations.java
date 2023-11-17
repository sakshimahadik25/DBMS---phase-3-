import java.sql.*;

public class VehicleOperations {
    public static void VehicleChoice(int VehicleChoice, Connection DB) {
        switch (VehicleChoice) {
            case 0:
                return;

            case 1:
                EnterVehicleOwnershipInfo(DB);
                break;

            case 2:
                UpdateVehicleOwnershipInfo(DB);
                break;

            case 3:
                DeleteVehicleOwnershipInfo(DB);
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void EnterVehicleOwnershipInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String CarLicenseNumber = UserInput.getString("\nEnter the Car License Number of the vehicle");
        String DriverID = UserInput.getString("Enter driver's ID who owns the vehicle");
        String Model = UserInput.getString("Enter the vehicle model");
        int Year = UserInput.getInt("Enter the vehicle year");
        String Color = UserInput.getString("Enter the vehicle color");
        String Manufacturer = UserInput.getString("Enter the vehicle manufacturer");
        String query = "INSERT into Vehicles " +
                "VALUES ('" + CarLicenseNumber + "', '" + Model + "', " + Year + ", '" +
                Color + "', '" + Manufacturer + "', '" + DriverID + "')";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Vehicle Ownership information successfully added!");
            }

            else {
                System.out.println("\nError while adding Vehicle Ownership Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void UpdateVehicleOwnershipInfo(Connection DB) {
        Statement stmt = null;
        String query = "";
        System.out.println("\n-----------------------------------------");
        String CarLicenseNumber = UserInput
                .getString("\nEnter the vehicle's Car License Number whose information you want to update");
        System.out.println("\nEnter the field you want to update:" +
                "\n1. Driver's ID \n2. Model \n3. Year \n4. Color \n5. Manufacturer");
        int updateChoice = UserInput.getInt("\nEnter your choice");

        if (updateChoice == 1) {
            String DriverID = UserInput.getString("\nEnter driver's ID who owns the vehicle");
            query = "UPDATE Vehicles set DriverID = " + "'" + DriverID + "'" +
                    "WHERE CarLicenseNumber=" + "'" + CarLicenseNumber + "'";
        } else if (updateChoice == 2) {
            String Model = UserInput.getString("Enter the vehicle model");
            query = "UPDATE Vehicles set Model = " + "'" + Model + "'" +
                    "WHERE CarLicenseNumber = " + "'" + CarLicenseNumber + "'";
        } else if (updateChoice == 3) {
            int Year = UserInput.getInt("Enter the vehicle year");
            query = "UPDATE Vehicles set Year = " + "'" + Year + "'" +
                    "WHERE CarLicenseNumber = " + "'" + CarLicenseNumber + "'";
        }

        else if (updateChoice == 4) {
            String Color = UserInput.getString("Enter the vehicle color");
            query = "UPDATE Vehicles set Color = " + "'" + Color + "'" +
                    "WHERE CarLicenseNumber = " + "'" + CarLicenseNumber + "'";
        }

        else if (updateChoice == 5) {
            String Manufacturer = UserInput.getString("Enter the vehicle manufacturer");
            query = "UPDATE Vehicles set Manufacturer = " + "'" + Manufacturer + "'" +
                    "WHERE CarLicenseNumber = " + "'" + CarLicenseNumber + "'";
        }

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Vehicle Ownership information successfully updated!");
            }

            else {
                System.out.println("\nError while updating Vehicle Ownership Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void DeleteVehicleOwnershipInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String CarLicenseNumber = UserInput.getString("\nEnter Car License Number of the vehicle you want to delete");
        String query = "DELETE FROM Vehicles WHERE CarLicenseNumber = " + "'" + CarLicenseNumber + "'";
        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Vehicle Ownership information successfully deleted!");
            }

            else {
                System.out.println("\nError while deleting Vehicle Ownership Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
