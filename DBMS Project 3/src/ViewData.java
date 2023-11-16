import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewData {

    public static void ViewDataChoice(int choice){
        switch (choice){

        }
    }
    public static void getAllCitations(){
        String query = "SELECT * FROM Citations";
        Statement stmt = null;
        ResultSet result = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(query);

            System.out.println("\n--------------------- Citations ---------------------\n");
            System.out.printf("%15s %15s %15s %20s %10s %15s %15s %20s %15s %15s %10s", "Citation number", "Citation date", "Citation time", "Category", "Fee", "PaymentStatus", "AppealStatus", "CarLicenseNumber", "ParkingLot", "DriverID", "StaffID");
            while (result.next()) {
                System.out.printf("\n%15d %15s %15s %20s %10.2f %15s %15s %20s %15s %15s %10d", result.getInt("CitationNo"), result.getString("CitationDate"), result.getString("CitationTime"), result.getString("Category"), result.getFloat("Fee"), result.getString("PaymentStatus"), result.getString("AppealStatus"), result.getString("CarLicenseNumber"), result.getString("ParkingLotName"), result.getString("DriverID"), result.getInt("StaffID"));
            }
            System.out.println("\n");
        } catch (SQLException err) {
            System.out.println("Error while generating citations - " + err.getMessage());
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
                System.out.printf("%15s %15s %15s %20s %10s %15s %15s %20s %15s %15s %10s", "Citation number", "Citation date", "Citation time", "Category", "Fee", "PaymentStatus", "AppealStatus", "CarLicenseNumber", "ParkingLot", "DriverID", "StaffID");

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

                System.out.printf("\n%15d %15s %15s %20s %10.2f %15s %15s %20s %15s %15s %10d", citationNo, citationDate, citationTime, category, fee, paymentStatus, appealStatus, carLicenseNumber, parkingLot, driverID, staffID);

            } else {
                throw new Exception(String.format("Citation %d does not exist", citationNo));
            }
        } catch (SQLException err) {
            System.out.println("Error while fetching citation details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("Error - %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }
    }
}
