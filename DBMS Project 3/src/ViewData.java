import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class ViewData {

    public static void ViewDataChoice(int choice) {
        switch (choice) {
            case 1:
                getAllDrivers();
                break;
            case 2:
                getDriverByID();
                break;
            case 3:
                getAllParkingLots();
                break;
            case 4:
                getParkingLotByName();
                break;
            case 5:
                getAllZones();
                break;
            case 6:
                getZoneByID();
                break;
            case 7:
                getAllSpaces();
                break;
            case 8:
                getSpaceByID();
                break;
            case 9:
                getAllPermits();
                break;
            case 10:
                getPermitByID();
                break;
            case 11:
                getAllVehicles();
                break;
            case 12:
                getVehicleByID();
                break;
            case 13:
                getAllCitations();
                break;
            case 14:
                getCitationByID();
                break;
            default:
                System.out.println("\nInvalid Choice");
                break;
        }
    }

    public static void getDriverByID() {
        String DriverID = UserInput.getString("\nEnter Driver ID");
        String query = "SELECT * FROM Drivers WHERE DriverID = '" + DriverID + "'";
        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);
            if (result.next()) {
                System.out.printf("%11s %20s %6s", "DriverID", "Name", "Status");

                String Name = result.getString("Name");
                String Status = result.getString("Status");

                System.out.printf("\n%11s %20s %6s", DriverID, Name, Status);

            } else {
                throw new Exception(String.format("\nDriver ID %s does not exist", DriverID));
            }
        } catch (SQLException err) {
            System.out.println("\nError while fetching Driver details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllDrivers() {
        String query = "SELECT * FROM Drivers";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Drivers ---------------------\n");
            System.out.printf("%11s %20s %6s", "DriverID", "Name", "Status");
            while (result.next()) {
                String DriverID = result.getString("DriverID");
                String Name = result.getString("Name");
                String Status = result.getString("Status");

                System.out.printf("\n%11s %20s %6s", DriverID, Name, Status);
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while getting Drivers information - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getParkingLotByName() {
        String ParkingLotName = UserInput.getString("\nEnter Parking Lot Name");
        String query = "SELECT * FROM ParkingLots WHERE ParkingLotName = '" + ParkingLotName + "'";
        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);
            if (result.next()) {
                System.out.printf("%23s %250s", "Parking Lot", "Address");

                String Address = result.getString("Address");

                System.out.printf("\n%23s %250s", ParkingLotName, Address);

            } else {
                throw new Exception(String.format("\nParking Lot %s does not exist", ParkingLotName));
            }
        } catch (SQLException err) {
            System.out.println("\nError while fetching Parking Lot details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllParkingLots() {
        String query = "SELECT * FROM ParkingLots";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Parking Lots ---------------------\n");
            System.out.printf("%23s %250s", "Parking Lot", "Address");
            while (result.next()) {
                String ParkingLotName = result.getString("ParkingLotName");
                String Address = result.getString("Address");

                System.out.printf("\n%23s %250s", ParkingLotName, Address);
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while getting Parking Lots information - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getZoneByID() {
        String ZoneID = UserInput.getString("\nEnter Zone ID");
        String query = "SELECT ALL * FROM Zones WHERE ZoneID = '" + ZoneID + "'";
        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.printf("%10s %23s", "ZoneID", "ParkingLotName");
            while (result.next()) {
                String ParkingLotName = result.getString("ParkingLotName");

                System.out.printf("\n%10s %23s", ZoneID, ParkingLotName);

            } 
        } catch (SQLException err) {
            System.out.println("\nError while fetching Zone details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllZones() {
        String query = "SELECT * FROM Zones";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Parking Zones ---------------------\n");
            System.out.printf("%10s %23s", "ZoneID", "Parking Lot");
            while (result.next()) {
                String ZoneID = result.getString("ZoneID");
                String ParkingLotName = result.getString("ParkingLotName");

                System.out.printf("\n%10s %23s", ZoneID, ParkingLotName);
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while getting Zones information - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getSpaceByID() {
        int SpaceID = UserInput.getInt("\nEnter Space ID");
        String query = "SELECT ALL * FROM Spaces WHERE SpaceID = " + SpaceID;
        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);
            System.out.printf("%11s %23s %15s %20s", "SpaceID", "Parking Lot", "Space Type", "Availability Status");
            while (result.next()) {
                String ParkingLotName = result.getString("ParkingLotName");
                String SpaceType = result.getString("SpaceType");
                String AvailabilityStatus = result.getString("AvailabilityStatus");

                System.out.printf("\n%11s %23s %15s %20s", SpaceID, ParkingLotName, SpaceType, AvailabilityStatus);

            }
        } catch (SQLException err) {
            System.out.println("\nError while fetching Space details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllSpaces() {
        String query = "SELECT * FROM Spaces";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Parking Spaces ---------------------\n");
            System.out.printf("%11s %23s %15s %20s", "SpaceID", "Parking Lot", "Space Type", "Availability Status");

            while (result.next()) {
                int SpaceID = result.getInt("SpaceID");
                String ParkingLotName = result.getString("ParkingLotName");
                String SpaceType = result.getString("SpaceType");
                String AvailabilityStatus = result.getString("AvailabilityStatus");

                System.out.printf("\n%11s %23s %15s %20s", SpaceID, ParkingLotName, SpaceType, AvailabilityStatus);
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while getting Spaces information - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getPermitByID() {
        int PermitID = UserInput.getInt("\nEnter the Permit ID");
        String query = "SELECT * FROM Permits WHERE PermitID = " + PermitID;
        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);
            if (result.next()) {
                System.out.printf("%8s %12s %11s %15s %15s %10s %11s %16s %6s %22s %13s", "PermitID", "Permit Type",
                        "Start Date ", "Expiration Date", "Expiration Time", "Space Type", "DriverID",
                        "CarLicenseNumber", "ZoneID", "Parking Lot", "Driver Status");

                String PermitType = result.getString("PermitType");
                Date StartDate = result.getDate("StartDate");
                Date ExpirationDate = result.getDate("ExpirationDate");
                Time ExpirationTime = result.getTime("ExpirationTime");
                String SpaceType = result.getString("SpaceType");
                String DriverID = result.getString("DriverID");
                String CarLicenseNumber = result.getString("CarLicenseNumber");
                String ZoneID = result.getString("ZoneID");
                String ParkingLotName = result.getString("ParkingLotName");
                String DriverStatus = result.getString("DriverStatus");

                System.out.printf("\n%8d %12s %11s %15s %15s %10s %11s %16s %6s %22s %13s", PermitID, PermitType,
                        StartDate, ExpirationDate, ExpirationTime, SpaceType, DriverID, CarLicenseNumber, ZoneID,
                        ParkingLotName, DriverStatus);

            } else {
                throw new Exception(String.format("\nPermit ID %d does not exist", PermitID));
            }
        } catch (SQLException err) {
            System.out.println("\nError while fetching permit details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllPermits() {
        String query = "SELECT * FROM Permits";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Permits ---------------------\n");
            System.out.printf("%8s %12s %11s %15s %15s %10s %11s %16s %6s %22s %13s", "PermitID", "Permit Type",
                    "Start Date ", "Expiration Date", "Expiration Time", "Space Type", "DriverID", "CarLicenseNumber",
                    "ZoneID", "Parking Lot", "Driver Status");
            while (result.next()) {
                int PermitID = result.getInt("PermitID");
                String PermitType = result.getString("PermitType");
                Date StartDate = result.getDate("StartDate");
                Date ExpirationDate = result.getDate("ExpirationDate");
                Time ExpirationTime = result.getTime("ExpirationTime");
                String SpaceType = result.getString("SpaceType");
                String DriverID = result.getString("DriverID");
                String CarLicenseNumber = result.getString("CarLicenseNumber");
                String ZoneID = result.getString("ZoneID");
                String ParkingLotName = result.getString("ParkingLotName");
                String DriverStatus = result.getString("DriverStatus");

                System.out.printf("\n%8d %12s %11s %15s %15s %10s %11s %16s %6s %22s %13s", PermitID, PermitType,
                        StartDate, ExpirationDate, ExpirationTime, SpaceType, DriverID, CarLicenseNumber, ZoneID,
                        ParkingLotName, DriverStatus);
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while getting information about permits - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getVehicleByID() {
        String CarLicenseNumber = UserInput.getString("\nEnter CarLicenseNumber");
        String query = "SELECT * FROM Vehicles WHERE CarLicenseNumber = '" + CarLicenseNumber + "'";
        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);
            System.out.printf("%25s %15s %4s %20s %15s %11s", "Car License Number", "Model", "Year", "Color", "Manufacturer", "DriverID");
            while (result.next()) {
                String Model = result.getString("Model");
                int Year = result.getInt("Year");
                String Color = result.getString("Color");
                String Manufacturer = result.getString("Manufacturer");
                String DriverID = result.getString("DriverID");

                System.out.printf("\n%25s %15s %4d %20s %15s %11s", CarLicenseNumber, Model, Year, Color, Manufacturer, DriverID);

            } 
        } catch (SQLException err) {
            System.out.println("\nError while fetching Vehicle details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllVehicles() {
        String query = "SELECT * FROM Vehicles";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Vehicles ---------------------\n");
            System.out.printf("%25s %25s %4s %20s %15s %11s", "Car License Number", "Model", "Year", "Color", "Manufacturer", "DriverID");
            while (result.next()) {
                String CarLicenseNumber = result.getString("CarLicenseNumber");
                String Model = result.getString("Model");
                int Year = result.getInt("Year");
                String Color = result.getString("Color");
                String Manufacturer = result.getString("Manufacturer");
                String DriverID = result.getString("DriverID");

                System.out.printf("\n%25s %25s %4d %20s %15s %11s", CarLicenseNumber, Model, Year, Color, Manufacturer, DriverID);
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while getting Vehicle information - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getAllCitations() {
        String query = "SELECT * FROM Citations";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Citations ---------------------\n");
            System.out.printf("%15s %15s %15s %20s %10s %15s %15s %20s %15s %15s %10s", "Citation number",
                    "Citation date", "Citation time", "Category", "Fee", "PaymentStatus", "AppealStatus",
                    "CarLicenseNumber", "ParkingLot", "DriverID", "StaffID");
            while (result.next()) {
                System.out.printf("\n%15d %15s %15s %20s %10.2f %15s %15s %20s %15s %15s %10d",
                        result.getInt("CitationNo"), result.getString("CitationDate"), result.getString("CitationTime"),
                        result.getString("Category"), result.getFloat("Fee"), result.getString("PaymentStatus"),
                        result.getString("AppealStatus"), result.getString("CarLicenseNumber"),
                        result.getString("ParkingLotName"), result.getString("DriverID"), result.getInt("StaffID"));
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("\nError while generating citations - " + err.getMessage());
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }

    public static void getCitationByID() {
        int citationNo = UserInput.getInt("Enter citation number");
        String getCitationQuery = String.format("SELECT * FROM Citations WHERE CitationNo = %d", citationNo);

        Statement stmt = null;
        ResultSet result = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(getCitationQuery);
            if (result.next()) {
                System.out.printf("%15s %15s %15s %20s %10s %15s %15s %20s %15s %15s %10s", "Citation number",
                        "Citation date", "Citation time", "Category", "Fee", "PaymentStatus", "AppealStatus",
                        "CarLicenseNumber", "ParkingLot", "DriverID", "StaffID");

                String citationDate = result.getString("CitationDate");
                String citationTime = result.getString("CitationTime");
                String category = result.getString("Category");
                float fee = result.getFloat("Fee");
                String paymentStatus = result.getString("PaymentStatus");
                String appealStatus = result.getString("AppealStatus");
                String carLicenseNumber = result.getString("CarLicenseNumber");
                String parkingLot = result.getString("ParkingLotName");
                String driverID = result.getString("DriverID");
                int staffID = result.getInt("StaffID");

                System.out.printf("\n%15d %15s %15s %20s %10.2f %15s %15s %20s %15s %15s %10d", citationNo,
                        citationDate, citationTime, category, fee, paymentStatus, appealStatus, carLicenseNumber,
                        parkingLot, driverID, staffID);

            } else {
                throw new Exception(String.format("\nCitation %d does not exist", citationNo));
            }
        } catch (SQLException err) {
            System.out.println("\nError while fetching citation details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
            DatabaseConnection.close();
        }
    }
}
