import java.sql.*;

public class PermitOperations {
    public static void PermitsChoice(int PermitChoice, Connection DB) {
        switch (PermitChoice) {
            case 0:
                return;

            case 1:
                AssignPermit(DB); // Assign Permit to Driver
                break;

            case 2:
                UpdatePermitInfo(DB); // Update Permit Information
                break;

            case 3:
                DeletePermitInfo(DB); // Delete Permit
                break;

            default:
                System.out.println("\nInvalid Choice");
                break;
        }

    }

    public static void AssignPermit(Connection DB) {
        Statement stmt = null;
        ResultSet result = null;
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

        
        String selectQuery = "SELECT DriverID FROM Vehicles WHERE CarLicenseNumber = '" + CarLicenseNumber + "'";

        String query = "INSERT INTO Permits (PermitType, StartDate, ExpirationDate, ExpirationTime, SpaceType, DriverID, CarLicenseNumber, ZoneID, ParkingLotName, DriverStatus) "
                +
                "VALUES ('" + PermitType + "', '" + StartDate + "', " +
                "'" + ExpirationDate + "', '" + ExpirationTime + "', '" + SpaceType + "'" +
                ", '" + DriverID + "', '" + CarLicenseNumber + "', '" + ZoneID + "'" +
                ", '" + ParkingLotName + "', '" + DriverStatus + "')";

        try {
            stmt = DB.createStatement();
            int ans = 0;
            result = stmt.executeQuery(selectQuery);
            
            if(result.next()) {
                if(result.getString("DriverID") != DriverID) {
                    throw new Exception("\nError: Vehicle owner information does not match!");
                }
                else {
                    ans = stmt.executeUpdate(query);
                }
            }

            if (ans == 1) {
                System.out.println("\nCongratulations! Permit successfully assigned to the driver!");
            }

            else {
                System.out.println("\nError while assigning Permit to the driver!");
            }

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
            DatabaseConnection.close(result);
        }
    }

    public static void UpdatePermitInfo(Connection DB) {
        Statement stmt = null;
        ResultSet result = null;
        String query = "";
        String validatingQuery = "";
        ResultSet validateResult = null;
        System.out.println("\n-----------------------------------------");
        String PermitID = UserInput.getString("\nEnter Permit's ID whose information you want to update");
        String PermitType = "";
        String StartDate = "";
        String ExpirationDate = "";
        String ExpirationTime = "";
        String SpaceType = "";
        String DriverID = "";
        String CarLicenseNumber = "";
        String ZoneID = "";
        String ParkingLotName = "";
        String DriverStatus = "";

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

            PermitType = "";

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
        } else if (updateChoice == 2) {
            StartDate = UserInput.getString("Enter the Start Date of the permit in YYYY-MM-DD format");
        } else if (updateChoice == 3) {
            ExpirationDate = UserInput.getString("Enter the End Date of the permit in YYYY-MM-DD format");
        } else if (updateChoice == 4) {
            ExpirationTime = UserInput.getString("Enter the Expiration Time of the permit in HH:MM:SS format");
        } else if (updateChoice == 5) {
            int ST = UserInput.getInt("Enter Space Type for which the permit is issued:" +
                    "\n1. Electric" +
                    "\n2. Handicap" +
                    "\n3. Compact Car" +
                    "\n4. Regular" +
                    "\nEnter the option number");

            SpaceType = "";

            if (ST == 1) {
                SpaceType = "electric";
            } else if (ST == 2) {
                SpaceType = "handicap";
            } else if (ST == 3) {
                SpaceType = "compact car";
            } else if (ST == 4) {
                SpaceType = "regular";
            }
        } else if (updateChoice == 6) {
            DriverID = UserInput.getString("Enter the Driver's ID");
        } else if (updateChoice == 7) {
            CarLicenseNumber = UserInput.getString("Enter the Car License Number");
        } else if (updateChoice == 8) {
            ZoneID = UserInput.getString("Enter the Zone ID");
        } else if (updateChoice == 9) {
            ParkingLotName = UserInput.getString("Enter the Parking Lot Name");
        } else if (updateChoice == 10) {
            DriverStatus = UserInput.getString("Enter Driver Status:" +
                    "\n1. If Student, enter S" +
                    "\n2. If Employee, enter E" +
                    "\n3. If Visitor, enter V" +
                    "\nEnter");
        }

        try {
            /* To keep the data consistent while updating Permits*/ 
            DB.setAutoCommit(false); // Transaction creation
            stmt = DB.createStatement();
            String selectQuery = "SELECT * FROM Permits WHERE PermitID = " + PermitID;
            String deleteQuery = "DELETE FROM Permits WHERE PermitID = " + PermitID;
            result = stmt.executeQuery(selectQuery); // Fetching the Permit

            if(result.next()) {
                PermitType = PermitType != "" ? PermitType : result.getString("PermitType");
                StartDate = StartDate != "" ? StartDate : result.getString("StartDate");
                ExpirationDate = ExpirationDate != "" ? ExpirationDate : result.getString("ExpirationDate");
                ExpirationTime = ExpirationTime != "" ? ExpirationTime : result.getString("ExpirationTime");
                SpaceType = SpaceType != "" ? SpaceType : result.getString("SpaceType");
                DriverID = DriverID != "" ? DriverID : result.getString("DriverID");
                CarLicenseNumber = CarLicenseNumber != "" ? CarLicenseNumber : result.getString("CarLicenseNumber");
                ZoneID = ZoneID != "" ? ZoneID : result.getString("ZoneID");
                ParkingLotName = ParkingLotName != "" ? ParkingLotName : result.getString("ParkingLotName");
                DriverStatus = DriverStatus != "" ? DriverStatus : result.getString("DriverStatus");
            }

            if(updateChoice == 6 || updateChoice == 7) {
                // Checking if the vehicle belongs to the correct owner
                validatingQuery = "SELECT DriverID FROM Vehicles WHERE CarLicenseNumber = '" + CarLicenseNumber + "'";
            }

            validateResult = stmt.executeQuery(validatingQuery);
            if(validateResult.next()) {
                if(validateResult.getString("DriverID") != DriverID) {
                    throw new SQLException("Vehicle owner information does not match!");
                }
            }

            query = "INSERT INTO Permits (PermitID, PermitType, StartDate, ExpirationDate, ExpirationTime, SpaceType, DriverID, CarLicenseNumber, ZoneID, ParkingLotName, DriverStatus) "
                    + "VALUES (" + PermitID + ", '" + PermitType + "', '" + StartDate + "', " +
                    "'" + ExpirationDate + "', '" + ExpirationTime + "', '" + SpaceType + "'" +
                    ", '" + DriverID + "', '" + CarLicenseNumber + "', '" + ZoneID + "'" +
                    ", '" + ParkingLotName + "', '" + DriverStatus + "')";

            stmt.executeUpdate(deleteQuery); // Deleting the permit

            int ans = stmt.executeUpdate(query); // Inserting the permit with updated value
            DB.commit();

            if (ans == 1) {
                System.out.println("\nCongratulations! Permit information successfully updated!");
            }

            else {
                System.out.println("\nError while updating Permit Information!");
            }
            DB.setAutoCommit(true);

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
            if (DB != null) {
                try {
                  System.err.println("\nTransaction is being rolled back");
                  DB.rollback(); // Roll back if any error occurs
                  DB.setAutoCommit(true);
                } catch (SQLException excep) {
                  System.err.println("\nError:" + excep.getMessage());
                }
              }
        } finally {
            DatabaseConnection.close(stmt);
            DatabaseConnection.close(result);
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

        } catch (SQLException oops) {
            System.err.println("\nError:" + oops.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }
}
