import constants.AppealStatus;
import constants.PaymentStatus;
import constants.SpaceType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportOperations {
    public static void citationReport(){
        String query = "SELECT * FROM Citations";
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Citation Report ---------------------\n");
            System.out.printf("%15s %15s %15s %20s %10s %15s %15s %20s %15s %15s %10s", "Citation number", "Citation date", "Citation time", "Category", "Fee", "PaymentStatus", "AppealStatus", "CarLicenseNumber", "ParkingLot", "DriverID", "StaffID");
            while(result.next()){
                System.out.printf("\n%15d %15s %15s %20s %10.2f %15s %15s %20s %15s %15s %10d", result.getInt("CitationNo"), result.getString("CitationDate"), result.getString("CitationTime"), result.getString("Category"), result.getFloat("Fee"), result.getString("PaymentStatus"), result.getString("AppealStatus"), result.getString("CarLicenseNumber"), result.getString("ParkingLotName"), result.getString("DriverID"), result.getInt("StaffID"));
            }
            System.out.println("\n");
        } catch(SQLException err){
            System.out.println("Error while generating citations - " + err.getMessage());
        }
    }

    public static void monthlyCitationReport(){
        String query = "SELECT ParkingLotName, MONTHNAME(CitationDate) AS Month, COUNT(*) AS TotalCitations FROM Citations GROUP BY ParkingLotName , MONTHNAME(CitationDate);";
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println("\n-------------------------------- Monthly Citation Report --------------------------------\n");
            System.out.printf("%20s %20s %20s", "Parking Lot", "Month", "Total Citations");
            while(result.next()){
                System.out.format("\n%20s %20s %20d", result.getString("ParkingLotName"), result.getString("Month"), result.getInt("TotalCitations"));
            }
            System.out.println("\n\n-----------------------------------------------------------------------------------------\n");

        } catch(SQLException err){
            System.out.println("Error while generating monthly citation report - " + err.getMessage());
        }
    }

    public static void annualCitationReport(){
        String query = "SELECT ParkingLotName, YEAR(CitationDate) AS Year, COUNT(*) AS TotalCitations FROM Citations GROUP BY ParkingLotName , YEAR(CitationDate);";
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println("\n-------------------------------- Annual Citation Report --------------------------------\n");
            System.out.printf("%20s %20s %20s", "Parking Lot", "Year", "Total Citations");
            while(result.next()){
                System.out.format("\n%20s %20s %20d", result.getString("ParkingLotName"), result.getString("Year"), result.getInt("TotalCitations"));
            }
            System.out.println("\n\n-----------------------------------------------------------------------------------------\n");

        } catch(SQLException err){
            System.out.println("Error while generating annual citation report - " + err.getMessage());
        }
    }

    public static void rangeBasedCitationReport(){
        String startDate = UserInput.getString("Enter start date");
        String endDate = UserInput.getString("Enter end date");
        String query = String.format("SELECT ParkingLotName, COUNT(*) AS TotalCitations FROM Citations WHERE CitationDate BETWEEN '%s' AND '%s' GROUP BY ParkingLotName;", startDate, endDate);
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println(String.format("\n------------------ Citation Report from %s to %s ------------------\n", startDate, endDate));
            System.out.printf("%20s %20s", "Parking Lot", "Total Citations");
            while(result.next()){
                System.out.format("\n%20s %20d", result.getString("ParkingLotName"), result.getInt("TotalCitations"));
            }
            System.out.println("\n\n-------------------------------------------------------------------------------\n");

        } catch(SQLException err){
            System.out.println("Error while generating citation report - " + err.getMessage());
        }
    }

    public static void listZonesForEachLot(){
        String query = "SELECT ParkingLotName, ZoneID FROM Zones ORDER BY ParkingLotName , ZoneID;";
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println(String.format("\n------------------ List of zones per lot ------------------\n"));
            System.out.printf("%20s %20s", "Parking Lot", "Zone");
            while(result.next()){
                System.out.format("\n%20s %20s", result.getString("ParkingLotName"), result.getString("ZoneID"));
            }
            System.out.println("\n\n-------------------------------------------------------------------------------\n");

        } catch(SQLException err){
            System.out.println("Error while listing zones for each lot - " + err.getMessage());
        }
    }

    public static void numberOfCarsInViolation(){
        String query = String.format("SELECT COUNT(DISTINCT CarLicenseNumber) AS ViolationCount FROM Citations WHERE PaymentStatus NOT IN ('%s','%s') AND AppealStatus != '%s';", PaymentStatus.PAID, PaymentStatus.WAIVED, AppealStatus.ACCEPT);
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println(String.format("\n------------------ Cars in violation ------------------\n"));
            System.out.printf("%30s", "Number of cars in violation");
            while(result.next()){
                System.out.format("\n%30d", result.getInt("ViolationCount"));
            }
            System.out.println("\n");

        } catch(SQLException err){
            System.out.println("Error while computing cars in violation - " + err.getMessage());
        }
    }

    public static void numberOfEmployeesHavingPermitsForGivenZone(){
        String zone = UserInput.getString("Enter zone").toUpperCase();
        String query = String.format("SELECT COUNT(*) AS EmployeeCount FROM Permits WHERE DriverStatus IN ('E') AND ZoneID = '%s';",zone);
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println(String.format("\n------------------ Number of employees having permits in Zone %s ------------------\n", zone));
            System.out.printf("%30s", "Number of employees");
            while(result.next()){
                System.out.format("\n%30d", result.getInt("EmployeeCount"));
            }
            System.out.println("\n");

        } catch(SQLException err){
            System.out.println("Error while computing employee count - " + err.getMessage());
        }
    }

    public static void permitInformation(){
        String driverID = UserInput.getString("Enter driver ID");
        String query = String.format("SELECT * FROM Permits WHERE DriverID = '%s';", driverID);
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println(String.format("\n------------------ Permit information ------------------\n"));
            System.out.printf("%10s %15s %15s %20s %20s %15s %20s %8s %15s %15s", "Permit ID", "Permit type", "Start date", "Expiration date", "Expiration time", "Space type", "Car license number", "Zone", "Parking lot", "Driver status");
            while(result.next()){
                System.out.format("\n%10d %15s %15s %20s %20s %15s %20s %8s %15s %15s", result.getInt("PermitID"), result.getString("PermitType"), result.getString("StartDate"), result.getString("ExpirationDate"), result.getString("ExpirationTime"),result.getString("SpaceType"),result.getString("CarLicenseNumber"),result.getString("ZoneID"),result.getString("ParkingLotName"),result.getString("DriverStatus"));
            }
            System.out.println("\n");

        } catch(SQLException err){
            System.out.println("Error while fetching permit information - " + err.getMessage());
        }
    }

    public static void spaceAvailability(){
        String parkingLot = UserInput.getString("Enter parking lot");
        System.out.print("Select space type: ");
        String spaceTypes[] = SpaceType.getSpaceTypes();
        String spaceType = displayOptions(spaceTypes);
        String query = String.format("SELECT SpaceID, ParkingLotName FROM Spaces WHERE SpaceType = '%s' AND AvailabilityStatus = 'Available' AND ParkingLotName = '%s';", spaceType, parkingLot);
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(query);

            System.out.println(String.format("\n------------------ Available spaces in %s for %s space ------------------\n", parkingLot, spaceType));
            if(result.next()){
                System.out.printf("%10s", "Space ID");
                do{
                    System.out.format("\n%10d", result.getInt("SpaceID"));
                } while(result.next());
            } else {
                System.out.println(String.format("No space available for %s in %s", spaceType, parkingLot));
            }

            System.out.println("\n");

        } catch(SQLException err){
            System.out.println("Error while fetching space availability - " + err.getMessage());
        }
    }

    private static String displayOptions(String[] list) {
        for (int j = 0; j < list.length; j++) {
            System.out.print((j + 1) + ". " + list[j] + "  ");
        }
        System.out.println();
        return list[UserInput.getInt("Enter choice") - 1];
    }
}
