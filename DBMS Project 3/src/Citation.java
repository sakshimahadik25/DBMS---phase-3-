import constants.AppealStatus;
import constants.PaymentStatus;
import constants.PermitCategory;
import constants.SpaceType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Citation {
    private static HashMap<String, Float> categoryFee = new HashMap<String, Float>() {{
        put(PermitCategory.INVALID_PERMIT, (float) 25);
        put(PermitCategory.EXPIRED_PERMIT, (float) 30);
        put(PermitCategory.NO_PERMIT, (float) 40);
    }};
    private static String[] attributeList = {"CitationNo", "CitationDate", "CitationTime", "Category", "Fee", "PaymentStatus", "AppealStatus", "CarLicenseNumber", "ParkingLotName", "DriverID", "StaffID"};

    private static String stringify(String s) {
        return "\'" + s + "\'";
    }

    public static void CitationChoice(int citationChoice) throws SQLException{
        switch (citationChoice){
            case 0:
                return;
            case 1:
                Citation.create();
                break;
            case 2:
                Citation.update();
                break;
            case 3:
                Citation.delete();
                break;
            case 4:
                Citation.detectParkingViolations();
                break;
            case 5:
                Citation.appealCitation();
                break;
            case 6:
                Citation.payForCitation();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }




    /**
     * This method takes citation number from user and delete it from the database. If there is no row manipulation, then it will throw error.
     */
    public static void delete() {
        int citationNo = UserInput.getInt("Enter citation number");
        String query = "DELETE FROM Citations WHERE CitationNo=" + citationNo;

        Statement stmt = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            int deleteCount = stmt.executeUpdate(query);
            if (deleteCount > 0) {
                System.out.println("\nSuccess: Citation (" + citationNo + ") deleted");
            } else {
                System.out.println("\nError: Citation (" + citationNo + ") does not exist");
            }
        } catch (SQLException err) {
            System.out.println("\nError while generating citation: " + err.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }




    /**
     * 1. Detects parking violation
     * 2. If there is any violation, it proceeds to generate a citation. It will take all parameters from user (except for citation number, citation date, and citation time).
     */
    public static void create() {
        boolean parkingViolations = detectParkingViolations();
        if (parkingViolations) {
            System.out.println("\n--------- Generating citation ---------");
            String citationDate = LocalDate.now().toString();
            String citationTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:s"));

            System.out.print("Select category: ");
            String categories[] = PermitCategory.getCategories();
            String category = displayOptions(categories);

            String paymentStatus = PaymentStatus.DUE;
            String appealStatus = AppealStatus.NO_APPEAL;
            String carLicenseNumber = UserInput.getString("Enter car license number");
            String parkingLotName = UserInput.getString("Enter parking lot");

            System.out.println("Is driver handicap?");
            String[] handicapOptions = new String[]{"Yes", "No"};
            boolean isHandicap = displayOptions(handicapOptions).equals("Yes");

            String driverID = UserInput.getString("Enter driver ID");
            int staffID = UserInput.getInt("Enter staff ID");

            float fee = categoryFee.get(category);
            if (isHandicap) fee /= 2;

            String query = "INSERT INTO Citations (";
            for (int i = 1; i < attributeList.length; i++) {
                query += attributeList[i];
                if (i != attributeList.length - 1) query += ",";
            }
            query += ") VALUES (";
            query += stringify(citationDate) + "," + stringify(citationTime) + "," + stringify(category) + "," + fee + "," + stringify(paymentStatus) + "," + stringify(appealStatus) + "," + stringify(carLicenseNumber) + "," + stringify(parkingLotName) + "," + stringify(driverID) + "," + staffID;
            query += ");";

            Statement stmt = null;

            try {
                Connection DB = DatabaseConnection.getDBInstance();
                stmt = DB.createStatement();
                boolean createStatus = stmt.execute(query);
                if (!createStatus) {
                    System.out.println("Success: Citation generated");
                }
            } catch (SQLException err) {
                System.out.println("Error while generating citation: " + err.getMessage());
            }  finally {
                DatabaseConnection.close(stmt);
            }
        }
    }




    /**
     * 1. This method updates the citation information in the database.
     * 2. The transaction begins by taking citation number from user.
     * 3. The citation information is validated and then deleted from database.
     * 4. New citation information is then inserted into database.
     * 5. During any of the Get, Delete, Insert citation operation, if any error occurs, then the transaction is rolled back.
     * @throws SQLException
     */
    public static void update() throws SQLException {

        Statement stmt = null;
        ResultSet result = null;
        int citationNo = UserInput.getInt("Enter citation number");
        String deleteQuery = String.format("DELETE FROM Citations WHERE CitationNo = %d", citationNo);

        // Fetch citation
        String getCitationQuery = String.format("SELECT * FROM Citations WHERE CitationNo = %d", citationNo);
        Connection DB = DatabaseConnection.getDBInstance();
        try {
            // Transaction begins
            DB.setAutoCommit(false);
            stmt = DB.createStatement();
            result = stmt.executeQuery(getCitationQuery);
            if (!result.next()) {
                // If it is an invalid citation number, then the transaction is rolled back
                DB.rollback();
                throw new Exception(String.format("Citation %d does not exist", citationNo));
            } else {
                // Delete previous citation
                int deleteCount = stmt.executeUpdate(deleteQuery);
                if(deleteCount == 0) {
                    // If error occurs while deleting the citation, then the transaction is rolled back
                    DB.rollback();
                    throw new Exception(String.format("Error while updating citation %d", citationNo));
                }

                String[] newAttributeList = Arrays.stream(attributeList).filter(attr -> !attr.equals("Fee")).toArray(String[]::new);

                System.out.println("\n--------- Select attribute to update ---------");
                for (int i = 1; i < newAttributeList.length; i++) {
                    System.out.println(i + " " + newAttributeList[i]);
                }
                String choice = UserInput.getString("Enter attribute");

                String citationDate = result.getString("CitationDate");
                String citationTime = result.getString("CitationTime");
                float fee = result.getFloat("Fee");
                String paymentStatus = result.getString("PaymentStatus");
                String appealStatus = result.getString("AppealStatus");
                String carLicenseNumber = result.getString("CarLicenseNumber");
                String parkingLotName = result.getString("ParkingLotName");
                String driverID = result.getString("DriverID");
                int staffID = result.getInt("StaffID");
                String category = result.getString("Category");

                String query = "INSERT INTO Citations (CitationNo,CitationDate,CitationTime,Fee,PaymentStatus,AppealStatus,CarLicenseNumber,ParkingLotName,DriverID,StaffID,Category) VALUES (%s);";
                System.out.println("\nEnter new value");

                String attr = newAttributeList[Integer.parseInt(choice)];
                if (attr == "Category") {
                    System.out.print("Select category: ");
                    String[] categories = PermitCategory.getCategories();
                    String selectedCategory = displayOptions(categories);

                    System.out.print("Is driver handicap? ");
                    String[] handicapOptions = new String[]{"Yes", "No"};
                    boolean isHandicap = displayOptions(handicapOptions).equals("Yes");

                    float defaultFee = categoryFee.get(selectedCategory);
                    if (isHandicap) defaultFee /= 2;

                    fee = defaultFee;
                    category = selectedCategory;
                } else if (attr == "AppealStatus") {
                    System.out.print("Select appeal status: ");
                    String[] appealStatuses = AppealStatus.getAppealStatuses();
                    String selectedAppealStatus = displayOptions(appealStatuses);
                    appealStatus = selectedAppealStatus;
                    if (selectedAppealStatus.equals(AppealStatus.ACCEPT))
                        paymentStatus = PaymentStatus.WAIVED;
                    else if (selectedAppealStatus.equals(AppealStatus.IN_PROGRESS))
                          paymentStatus = PaymentStatus.DUE;
                } else if (attr == "PaymentStatus") {
                    System.out.print("Select payment status: ");
                    String[] paymentStatuses = PaymentStatus.getPaymentStatuses();
                    String selectedPaymentStatus = displayOptions(paymentStatuses);
                    paymentStatus = selectedPaymentStatus;
                    if (selectedPaymentStatus.equals(PaymentStatus.WAIVED))
                        appealStatus = AppealStatus.ACCEPT;
                    else if (selectedPaymentStatus.equals(PaymentStatus.PAID)) {
                        if (result.getString("AppealStatus").equals(AppealStatus.IN_PROGRESS))
                            appealStatus = AppealStatus.REJECT;
                    }
                } else {
                    switch (attr){
                        case "StaffID":
                            staffID = UserInput.getInt("Enter " + attr);
                            break;
                        case "CitationDate":
                            citationDate = UserInput.getString("Enter " + attr);
                            break;
                        case "CitationTime":
                            citationTime = UserInput.getString("Enter " + attr);
                            break;
                        case "DriverID":
                            driverID = UserInput.getString("Enter " + attr);
                            break;
                        case "CarLicenseNumber":
                            carLicenseNumber = UserInput.getString("Enter " + attr);
                            break;
                        case "ParkingLotName":
                            parkingLotName = UserInput.getString("Enter " + attr);
                            break;
                    }

                }


                String values = "";
                values += citationNo + "," +  stringify(citationDate) + "," + stringify(citationTime) + "," + fee + "," + stringify(paymentStatus) + "," + stringify(appealStatus) + "," + stringify(carLicenseNumber) + "," + stringify(parkingLotName) + "," + stringify(driverID) + "," + staffID + "," + stringify(category);
                query = String.format(query, values);
                stmt = DB.createStatement();

                // Insert new citation
                int updateStatus = stmt.executeUpdate(query);
                if (updateStatus > 0) {
                    // If the citation is inserted successfully, then the transaction is committed
                    DB.commit();
                    System.out.println("Citation updated successfully");
                } else {
                    // If error occurs while inserting the citation, the transaction is rolled back
                    DB.rollback();throw new Exception(String.format("Error while updating citation %d", citationNo));
            }
        }
        } catch (SQLException err) {
            System.out.println("\nError while fetching citation details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError: %s", e.getMessage()));
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
            DB.setAutoCommit(true);
        }
    }




    /**
     * This method help to detect any parking violations. It takes CarLicenseNumber, ParkingLotName, and ZoneID from user.
     * @return true if there are any parking violation for the car, otherwise false.
     */
    public static boolean detectParkingViolations() {
        String carLicenseNumber = UserInput.getString("Enter car license number");
        String parkingLot = UserInput.getString("Enter parking lot");
        String zoneID = UserInput.getString("Enter zone ID");

        System.out.print("Select space type: ");
        String[] spaceTypes = SpaceType.getSpaceTypes();
        String spaceType = displayOptions(spaceTypes);

        String query = "SELECT * FROM Permits WHERE CarLicenseNumber = ? AND ParkingLotName = ? AND ZoneID = ? AND SpaceType = ? AND DATEDIFF(NOW(), StartDate) > 0 AND (DATEDIFF(ExpirationDate, NOW()) > 0 OR (DATEDIFF(ExpirationDate, NOW()) = 0 AND time_to_sec(TIMEDIFF(CONCAT_WS(' ', ExpirationDate, ExpirationTime), NOW()) > 0))) > 0;";

        PreparedStatement stmt = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.prepareStatement(query);
            stmt.setString(1, carLicenseNumber);
            stmt.setString(2, parkingLot);
            stmt.setString(3, zoneID);
            stmt.setString(4, spaceType);

            ResultSet parkingViolations = stmt.executeQuery();

            if (parkingViolations.first()) {
                System.out.println("\nStatus: The car has a valid permit");
                System.out.println(String.format("\n------------------ Permit information ------------------\n"));
                System.out.printf("%10s %15s %15s %20s %20s %20s %15s %20s %8s %20s %15s", "PermitID", "PermitType", "DriverID", "StartDate", "ExpirationDate", "ExpirationTime", "SpaceType", "CarLicenseNumber", "Zone", "ParkingLot", "DriverStatus");
                do {
                    System.out.println();
                    int permitID = parkingViolations.getInt("PermitID");
                    String permitType = parkingViolations.getString("PermitType");
                    String startDate = parkingViolations.getString("StartDate");
                    String expirationDate = parkingViolations.getString("ExpirationDate");
                    String expirationTime = parkingViolations.getString("ExpirationTime");
                    String driverID = parkingViolations.getString("DriverID");
                    String driverStatus = parkingViolations.getString("DriverStatus");

                    System.out.printf("\n%10d %15s %15s %20s %20s %20s %15s %20s %8s %20s %15s", permitID, permitType, driverID, startDate, expirationDate, expirationTime, spaceType, carLicenseNumber, zoneID, parkingLot, driverStatus);

                } while (parkingViolations.next());
                System.out.println("\n");
                return false;
            } else {
                System.out.println("\nViolation detected: The car has no valid permit");
                return true;
            }

        } catch (SQLException err) {
            System.out.println("\nError while detecting parking violations: " + err.getMessage());
        }  finally {
            DatabaseConnection.close(stmt);
        }
        return false;
    }




    /**
     * It allows the user to appeal the citation. It takes CitationNo from user.
     */
    public static void appealCitation() {
        String query = String.format("UPDATE Citations SET AppealStatus = '%s', PaymentStatus = '%s' WHERE CitationNo = ?", AppealStatus.IN_PROGRESS, PaymentStatus.DUE);
        int citationNo = UserInput.getInt("Enter citation number");
        PreparedStatement stmt = null;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.prepareStatement(query);
            stmt.setInt(1, citationNo);

            int updateCount = stmt.executeUpdate();
            if (updateCount > 0) {
                System.out.println(String.format("\nSuccess: Appeal generated for citation (%d)", citationNo));
            } else {
                System.out.println(String.format("\nError: Citation (%d) does not exist", citationNo));
            }

        } catch (SQLException err) {
            System.out.println("\nError while detecting parking violations: " + err.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
        }
    }




    /**
     * This method allows user to pay for the citation. It takes citation number from the user.
     */
    public static void payForCitation() {
        int citationNo = UserInput.getInt("Enter citation number");
        String getCitationQuery = String.format("SELECT * FROM Citations WHERE CitationNo = %d", citationNo);
        String appealStatus = "";

        Statement stmt = null;
        ResultSet result = null;
        PreparedStatement prep_stmt = null;

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            stmt = DB.createStatement();
            result = stmt.executeQuery(getCitationQuery);
            if (result.next()) {
                appealStatus = result.getString("AppealStatus");
            } else {
                throw new Exception(String.format("Citation %d does not exist", citationNo));
            }
        } catch (SQLException err) {
            System.out.println("\nError while fetching citation details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("\nError: %s", e.getMessage()));
            return;
        } finally {
            DatabaseConnection.close(result);
            DatabaseConnection.close(stmt);
        }

        String query = String.format("UPDATE Citations SET PaymentStatus = '%s', AppealStatus = '%s' WHERE CitationNo = %d;", PaymentStatus.PAID, appealStatus.equals(AppealStatus.IN_PROGRESS) ? AppealStatus.REJECT : AppealStatus.NO_APPEAL, citationNo);

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            prep_stmt = DB.prepareStatement(query);

            int updateCount = prep_stmt.executeUpdate();
            if (updateCount > 0) {
                System.out.println(String.format("\nSuccess: Paid for citation (%d)", citationNo));
            } else {
                System.out.println(String.format("\nError: Unable to pay for citation (%d)", citationNo));
            }

        } catch (SQLException err) {
            System.out.println("\nError while paying for citations: " + err.getMessage());
        } finally {
            DatabaseConnection.close(stmt);
            DatabaseConnection.close(prep_stmt);
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
