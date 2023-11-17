import java.sql.*;

public class SpacesOperations {

    public static void SpacesChoice(int SpacesChoice, Connection DB) {
        switch (SpacesChoice) {
            case 1:
                AddSpace(DB);
                break;

            case 2:
                UpdateSpaceInfo(DB);
                break;

            case 3:
                DeleteSpaceInfo(DB);
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void AddSpace(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        int SpaceID = UserInput.getInt("\nEnter Space ID");
        String ParkingLotName = UserInput.getString("Enter Parking Lot name");
        int ST = UserInput.getInt("Enter Space Type of the space:" +
                "\n1. Electric" +
                "\n2. Handicap" +
                "\n3. Compact Car" +
                "\n4. Regular" +
                "\nEnter the option number");

        String SpaceType = "";

        if (ST == 1) {
            SpaceType = "electric";
        } else if (ST == 2) {
            SpaceType = "handicap";
        } else if (ST == 3) {
            SpaceType = "compact car";
        } else if (ST == 4) {
            SpaceType = "regular";
        }

        String AvailabilityStatus = UserInput.getString("Enter the Availability Status of the space");

        String query = "INSERT INTO Spaces (SpaceID, ParkingLotName, SpaceType, AvailabilityStatus)" +
                "VALUES (" + SpaceID + ", '" + ParkingLotName + "', '" + SpaceType + "', '" + AvailabilityStatus + "')";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! New Parking Space successfully added to the given Parking Lot!");
            }

            else {
                System.out.println("\nError while adding new parking space!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void UpdateSpaceInfo(Connection DB) {
        Statement stmt = null;
        String query = "";
        System.out.println("\n-----------------------------------------");
        int SpaceID = UserInput.getInt("\nEnter Space ID of the space you want to update");
        String ParkingLotName = UserInput.getString("Enter Parking Lot name of that space");
        System.out.println("\nEnter the field you want to update:" +
                "\n1. Space Type \n2. Availability Status \n3. Parking Lot Name");

        int updateChoice = UserInput.getInt("\nEnter your choice");
        if (updateChoice == 1) {
            int ST = UserInput.getInt("Enter Space Type of the space:" +
                    "\n1. Electric" +
                    "\n2. Handicap" +
                    "\n3. Compact Car" +
                    "\n4. Regular" +
                    "\nEnter the option number");

            String SpaceType = "";

            if (ST == 1) {
                SpaceType = "electric";
            } else if (ST == 2) {
                SpaceType = "handicap";
            } else if (ST == 3) {
                SpaceType = "compact car";
            } else if (ST == 4) {
                SpaceType = "regular";
            }

            query = "UPDATE Spaces SET " +
                    "SpaceType = '" + SpaceType + "' WHERE " +
                    "SpaceID = " + SpaceID + " AND ParkingLotName = '" + ParkingLotName + "'";

        } else if (updateChoice == 2) {
            String AvailabilityStatus = UserInput.getString("Enter the Availability Status of the space");
            query = "UPDATE Spaces SET " +
                    "AvailabilityStatus = '" + AvailabilityStatus + "' WHERE " +
                    "SpaceID = " + SpaceID + " AND ParkingLotName = '" + ParkingLotName + "'";
        } else if (updateChoice == 3) {
            String updateParkingLotName = UserInput.getString("Enter the Parking Lot Name");
            query = "UPDATE Spaces SET " +
                    "ParkingLotName = '" + updateParkingLotName + "' WHERE " +
                    "SpaceID = " + SpaceID + " AND ParkingLotName = '" + ParkingLotName + "'";
        }

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Parking Space information successfully updated!");
            }

            else {
                System.out.println("\nError while updating Parking Space Information!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void DeleteSpaceInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        int SpaceID = UserInput.getInt("\nEnter Space ID of the space you want to delete");
        String ParkingLotName = UserInput.getString("Enter Parking Lot name of that space");
        String query = "DELETE FROM Spaces WHERE SpaceID = " + SpaceID + " AND ParkingLotName = '" + ParkingLotName
                + "'";
        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Parking Space successfully deleted!");
            }

            else {
                System.out.println("\nError while deleting the parking Space!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
