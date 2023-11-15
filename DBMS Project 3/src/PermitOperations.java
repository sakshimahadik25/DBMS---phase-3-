import java.sql.*;

public class PermitOperations {
    public static void PermitsChoice(int PermitChoice, Connection DB) {
        switch (PermitChoice) {
            case 1:
                AssignPermit(DB);
                break;

            case 2:
                UpdatePermitInfo(DB);
                break;

            case 3:
                DeletePermitInfo(DB);
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void AssignPermit(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        int PT = UserInput.getInt("\nEnter Permit type:" +
                "\n1. Residential" +
                "\n2. Commuter" +
                "\n3. Peak Hours" +
                "\n4. Special Event" +
                "\n5. Park & Ride" +
                "\nEnter the option number");

        String PermitType = "";

        if (PT == 1) {
            PermitType = "residential";
        } else if (PT == 2) {
            PermitType = "commuter";
        } else if (PT == 3) {
            PermitType = "peak hours";
        } else if (PT == 4) {
            PermitType = "special event";
        } else if (PT == 5) {
            PermitType = "Park & Ride";
        }

        String StartDate = UserInput.getString("Enter the Start Date of the permit in YYYY-MM-DD format");
        String ExpirationDate = UserInput.getString("Enter the End Date of the permit in YYYY-MM-DD format");
        String ExpirationTime = UserInput.getString("Enter the Expiration Time of the permit in HH:MM:SS format");
        int ST = UserInput.getInt("Enter Space Type for which the permit is issued:" +
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

        String DriverID = UserInput.getString("Enter the Driver's ID");
        String CarLicenseNumber = UserInput.getString("Enter the Car License Number");
        String ZoneID = UserInput.getString("Enter the Zone ID");
        String ParkingLotName = UserInput.getString("Enter the Parking Lot Name");
        String DriverStatus = UserInput.getString("Enter Driver Status:" +
                "\n1. If Student, enter S" +
                "\n2. If Employee, enter E" +
                "\n3. If Visitor, enter V" +
                "\nEnter");

        String query = "INSERT INTO Permits (PermitType, StartDate, ExpirationDate, ExpirationTime, SpaceType, DriverID, CarLicenseNumber, ZoneID, ParkingLotName, DriverStatus) "
                +
                "VALUES ('" + PermitType + "', '" + StartDate + "', " +
                "'" + ExpirationDate + "', '" + ExpirationTime + "', '" + SpaceType + "'" +
                ", '" + DriverID + "', '" + CarLicenseNumber + "', '" + ZoneID + "'" +
                ", '" + ParkingLotName + "', '" + DriverStatus + "')";

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Permit successfully assigned to the driver!");
            }

            else {
                System.out.println("\nError while assigning Permit to the driver!");
            }

        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void UpdatePermitInfo(Connection DB) {
        Statement stmt = null;
        String query = "";
        System.out.println("\n-----------------------------------------");
        String PermitID = UserInput.getString("\nEnter Permit's ID whose information you want to update");
        System.out.println("\nEnter the field you want to update:" +
                "\n1. Permit Type" +
                "\n2. Start Date" +
                "\n3. Expiration Date" +
                "\n4. Expiration Time" +
                "\n5. Space Type" +
                "\n6. Driver ID" +
                "\n7. Car License Number" +
                "\n8. Zone ID" +
                "\n9. Parking Lot Name" +
                "\n10. Driver Status");
        int updateChoice = UserInput.getInt("\nEnter your choice");
        if (updateChoice == 1) {
            int PT = UserInput.getInt("\nEnter Permit type:" +
                    "\n1. Residential" +
                    "\n2. Commuter" +
                    "\n3. Peak Hours" +
                    "\n4. Special Event" +
                    "\n5. Park & Ride" +
                    "\nEnter the option number");

            String PermitType = "";

            if (PT == 1) {
                PermitType = "residential";
            } else if (PT == 2) {
                PermitType = "commuter";
            } else if (PT == 3) {
                PermitType = "peak hours";
            } else if (PT == 4) {
                PermitType = "special event";
            } else if (PT == 5) {
                PermitType = "Park & Ride";
            }
            query = "UPDATE Permits SET PermitType = '" + PermitType + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 2) {
            String StartDate = UserInput.getString("Enter the Start Date of the permit in YYYY-MM-DD format");

            query = "UPDATE Permits SET StartDate = '" + StartDate + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 3) {
            String ExpirationDate = UserInput.getString("Enter the End Date of the permit in YYYY-MM-DD format");
            query = "UPDATE Permits SET ExpirationDate = '" + ExpirationDate + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 4) {
            String ExpirationTime = UserInput.getString("Enter the Expiration Time of the permit in HH:MM:SS format");
            query = "UPDATE Permits SET ExpirationTime = '" + ExpirationTime + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 5) {
            int ST = UserInput.getInt("Enter Space Type for which the permit is issued:" +
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
            query = "UPDATE Permits SET SpaceType = '" + SpaceType + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 6) {
            String DriverID = UserInput.getString("Enter the Driver's ID");
            query = "UPDATE Permits SET DriverID = '" + DriverID + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 7) {
            String CarLicenseNumber = UserInput.getString("Enter the Car License Number");
            query = "UPDATE Permits SET CarLicenseNumber = '" + CarLicenseNumber + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 8) {
            String ZoneID = UserInput.getString("Enter the Zone ID");
            query = "UPDATE Permits SET ZoneID = '" + ZoneID + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 9) {
            String ParkingLotName = UserInput.getString("Enter the Parking Lot Name");
            query = "UPDATE Permits SET ParkingLotName = '" + ParkingLotName + "'" +
                    "WHERE PermitID = " + PermitID;

        } else if (updateChoice == 10) {
            String DriverStatus = UserInput.getString("Enter Driver Status:" +
                    "\n1. If Student, enter S" +
                    "\n2. If Employee, enter E" +
                    "\n3. If Visitor, enter V" +
                    "\nEnter");
            query = "UPDATE Permits SET DriverStatus = '" + DriverStatus + "'" +
                    "WHERE PermitID = " + PermitID;
        }

        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Permit information successfully updated!");
            }

            else {
                System.out.println("\nError while updating Permit Information!");
            }

        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            DatabaseConnection.close(stmt);
        }
    }

    public static void DeletePermitInfo(Connection DB) {
        Statement stmt = null;
        System.out.println("\n-----------------------------------------");
        String PermitID = UserInput.getString("\nEnter the Permit's ID you want to delete");
        String query = "DELETE FROM Permits WHERE PermitID = " + "'" + PermitID + "'";
        try {
            stmt = DB.createStatement();
            int ans = stmt.executeUpdate(query);

            if (ans == 1) {
                System.out.println("\nCongratulations! Permit information successfully deleted!");
            }

            else {
                System.out.println("\nError while deleting Permit Information!");
            }

        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
