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
    int citationNo;
    String citationDate;
    String citationTime;
    String category;
    float fee;
    String paymentStatus;
    String appealStatus;
    String carLicenseNumber;
    String parkingLotName;
    String driverID;
    int staffID;

    private static HashMap<String, Float> categoryFee = new HashMap<String, Float>() {{
        put(PermitCategory.INVALID_PERMIT, (float) 25);
        put(PermitCategory.EXPIRED_PERMIT, (float) 30);
        put(PermitCategory.NO_PERMIT, (float) 40);
    }};
    private static String[] attributeList = {"CitationNo", "CitationDate", "CitationTime", "Category", "Fee", "PaymentStatus", "AppealStatus", "CarLicenseNumber", "ParkingLotName", "DriverID", "StaffID"};

    private static String stringify(String s) {
        return "\'" + s + "\'";
    }

    public static void getCitation() {
        int citationNo = UserInput.getInt("Enter citation number");
        String getCitationQuery = String.format("SELECT * FROM Citations WHERE CitationNo = %d", citationNo);
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(getCitationQuery);
            if (result.next()) {
                System.out.println("\nCitation Number: " + result.getInt("CitationNo"));
                System.out.println("Citation Date: " + result.getString("CitationDate"));
                System.out.println("Citation Time: " + result.getString("CitationTime"));
                System.out.println("Category: " + result.getString("Category"));
                System.out.println("Fee: " + result.getFloat("Fee"));
                System.out.println("Payment Status: " + result.getString("PaymentStatus"));
                System.out.println("Appeal Status: " + result.getString("AppealStatus"));
                System.out.println("Car License Number: " + result.getString("CarLicenseNumber"));
                System.out.println("Parking Lot: " + result.getString("ParkingLotName"));
                System.out.println("Driver ID: " + result.getString("DriverID"));
                System.out.println("Staff ID: " + result.getString("StaffID"));
            } else {
                throw new Exception(String.format("Citation %d does not exist", citationNo));
            }
        } catch (SQLException err) {
            System.out.println("Error while fetching citation details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("Error - %s", e.getMessage()));
        }
    }

    public static void delete() {
        int citationNo = UserInput.getInt("Enter citation number");
        String query = "DELETE FROM Citations WHERE CitationNo=" + citationNo;
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            System.out.println("Query: " + query);
            int deleteCount = stmt.executeUpdate(query);
            if (deleteCount > 0) {
                System.out.println("Citation (" + citationNo + ") deleted successfully");
            } else {
                System.out.println("Citation (" + citationNo + ") does not exist");
            }
        } catch (SQLException err) {
            System.out.println("Error while generating citation: " + err.getMessage());
        }
    }

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
            boolean isHandicap = Integer.parseInt(displayOptions(handicapOptions)) == 1;

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

            System.out.println("Query: " + query);

            try {
                Connection DB = DatabaseConnection.getDBInstance();
                Statement stmt = DB.createStatement();
                System.out.println("Query: " + query);
                boolean createStatus = stmt.execute(query);
                if (!createStatus) {
                    System.out.println("Create count: " + stmt.getUpdateCount());
                }
                stmt.close();
            } catch (SQLException err) {
                System.out.println("Error while generating citation: " + err.getMessage());
            }
        }
    }

    public static void update() {
        try {
            int citationNo = UserInput.getInt("Enter citation number");

            // Fetch citation
            String getCitationQuery = String.format("SELECT * FROM Citations WHERE CitationNo = %d", citationNo);
            ResultSet result = null;
            try {
                Connection DB = DatabaseConnection.getDBInstance();
                Statement stmt = DB.createStatement();
                result = stmt.executeQuery(getCitationQuery);
                if (!result.next()) {
                    throw new Exception(String.format("Citation %d does not exist", citationNo));
                }
            } catch (SQLException err) {
                System.out.println("Error while fetching citation details: " + err.getMessage());
            } catch (Exception e) {
                System.out.println(String.format("Error - %s", e.getMessage()));
                return;
            }

            String[] newAttributeList = Arrays.stream(attributeList).filter(attr -> !attr.equals("Fee")).toArray(String[]::new);

            System.out.println("\n--------- Select attributes to update ---------");
            for (int i = 1; i < newAttributeList.length; i++) {
                System.out.println(i + " " + newAttributeList[i]);
            }
            String choice = UserInput.getString("Enter attribute");
//        String attributes[] = choice.split(",");

            String query = "UPDATE Citations SET ";
            ArrayList<String> updateConditions = new ArrayList<>();
            System.out.println("\nEnter new value");
//        String selectedAppealStatus = null;
//        String selectedPaymentStatus = null;

            String attr = newAttributeList[Integer.parseInt(choice)];
            if (attr == "Category") {
                System.out.print("Select category: ");
                String[] categories = PermitCategory.getCategories();
                String selectedCategory = displayOptions(categories);

                System.out.print("Is driver handicap? ");
                String[] handicapOptions = new String[]{"Yes","No"};
                boolean isHandicap = displayOptions(handicapOptions).equals("Yes");

                float fee = categoryFee.get(selectedCategory);
                if (isHandicap) fee /= 2;

                updateConditions.add("Fee" + "=" + fee);
                updateConditions.add(attr + "=" + stringify(selectedCategory));
            } else if (attr == "AppealStatus") {
                System.out.print("Select appeal status: ");
                String[] appealStatuses = AppealStatus.getAppealStatuses();
                String selectedAppealStatus = displayOptions(appealStatuses);
                updateConditions.add(attr + "=" + stringify(selectedAppealStatus));
                if (selectedAppealStatus.equals(AppealStatus.ACCEPT))
                    updateConditions.add("PaymentStatus" + "=" + stringify(PaymentStatus.WAIVED));
                else if (selectedAppealStatus.equals(AppealStatus.IN_PROGRESS))
                    updateConditions.add("PaymentStatus" + "=" + stringify(PaymentStatus.DUE));
            } else if (attr == "PaymentStatus") {
                System.out.print("Select payment status: ");
                String[] paymentStatuses = PaymentStatus.getPaymentStatuses();
                String selectedPaymentStatus = displayOptions(paymentStatuses);
                updateConditions.add(attr + "=" + stringify(selectedPaymentStatus));
                if (selectedPaymentStatus.equals(PaymentStatus.WAIVED))
                    updateConditions.add("AppealStatus" + "=" + stringify(AppealStatus.ACCEPT));
                else if (selectedPaymentStatus.equals(PaymentStatus.PAID)) {
                    if (result.getString("AppealStatus").equals(AppealStatus.IN_PROGRESS))
                        updateConditions.add("AppealStatus" + "=" + stringify(AppealStatus.REJECT));
                }
            } else {
                if (!attr.equals("StaffID")) {
                    updateConditions.add(attr + "=" + stringify(UserInput.getString("Enter " + attr)));
                } else {
                    updateConditions.add(attr + "=" + UserInput.getString("Enter " + attr));
                }
            }
//        for (int i = 0; i < attributes.length; i++) {
//            String attr = newAttributeList[Integer.parseInt(attributes[i])];
//            if (attr == "Category") {
//                System.out.print("Select category: ");
//                String [] categories = PermitCategory.getCategories();
//                String selectedCategory = displayOptions(categories);
//
//                System.out.println("Is driver handicap?");
//                String[] handicapOptions = new String[]{"Yes","No"};
//                boolean isHandicap = Integer.parseInt(displayOptions(handicapOptions)) == 1;
//
//                float fee = categoryFee.get(selectedCategory);
//                if(isHandicap) fee /= 2;
//
//                updateConditions.add("Fee" + "=" + fee);
//                updateConditions.add(attr + "=" + stringify(selectedCategory));
//
//            } else if(attr == "AppealStatus"){
//                System.out.print("Select appeal status: ");
//                String [] appealStatuses = AppealStatus.getAppealStatuses();
//                selectedAppealStatus = displayOptions(appealStatuses);
//            } else if(attr == "PaymentStatus"){
//                System.out.print("Select payment status: ");
//                String [] paymentStatuses = PaymentStatus.getPaymentStatuses();
//                selectedPaymentStatus = displayOptions(paymentStatuses);
//            } else {
//                if (!attr.equals("StaffID")) {
//                    updateConditions.add(attr + "=" + stringify(UserInput.getString("Enter " + attr)));
//                } else {
//                    updateConditions.add(attr + "=" + UserInput.getString("Enter " + attr));
//                }
//            }
//        }
//        if(selectedPaymentStatus != null){
//            if(selectedPaymentStatus.equals(PaymentStatus.PAID) && selectedAppealStatus != null && selectedAppealStatus.equals(AppealStatus.IN_PROGRESS)) selectedAppealStatus = AppealStatus.REJECT;
//            else if(selectedPaymentStatus.equals(PaymentStatus.WAIVED)) selectedAppealStatus = AppealStatus.ACCEPT;
//        }
//        if(selectedAppealStatus != null){
//            if(selectedAppealStatus.equals(AppealStatus.ACCEPT)) selectedPaymentStatus = PaymentStatus.WAIVED;
//            else if(selectedAppealStatus.equals(AppealStatus.IN_PROGRESS)) selectedPaymentStatus = PaymentStatus.DUE;
//        }

//        if(selectedPaymentStatus != null) updateConditions.add("PaymentStatus" + "=" + stringify(selectedPaymentStatus));
//        if(selectedAppealStatus != null) updateConditions.add("AppealStatus" + "=" + stringify(selectedAppealStatus));

            for (int i = 0; i < updateConditions.size(); i++) {
                query += updateConditions.get(i);
                if (i < updateConditions.size() - 1) query += ", ";
            }

            query += " WHERE CitationNo=" + citationNo + ";";


            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            System.out.println("Query: " + query);

            int updateStatus = stmt.executeUpdate(query);
            if (updateStatus > 0) {
                System.out.println("Citation updated successfully");
            } else throw new SQLException(String.format("Citation %d does not exist", citationNo));
        } catch (SQLException err) {
            System.out.println("Error while updating citation: " + err.getMessage());
        }
    }

    public static boolean detectParkingViolations() {
        String carLicenseNumber = UserInput.getString("Enter car license number");
        String parkingLot = UserInput.getString("Enter parking lot");
        String zoneID = UserInput.getString("Enter zone ID");

        System.out.print("Select space type: ");
        String[] spaceTypes = SpaceType.getSpaceTypes();
        String spaceType = displayOptions(spaceTypes);

        String query = "SELECT * FROM Permits WHERE CarLicenseNumber = ? AND ParkingLotName = ? AND ZoneID = ? AND SpaceType = ? AND DATEDIFF(NOW(), StartDate) > 0 AND (DATEDIFF(ExpirationDate, NOW()) > 0 OR (DATEDIFF(ExpirationDate, NOW()) = 0 AND time_to_sec(TIMEDIFF(CONCAT_WS(' ', ExpirationDate, ExpirationTime), NOW()) > 0))) > 0;";

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            PreparedStatement stmt = DB.prepareStatement(query);
            stmt.setString(1, carLicenseNumber);
            stmt.setString(2, parkingLot);
            stmt.setString(3, zoneID);
            stmt.setString(4, spaceType);
            System.out.println("\n" + carLicenseNumber + " " + parkingLot + " " + zoneID + " " + spaceType);
            System.out.println("Query: " + stmt);
            ResultSet parkingViolations = stmt.executeQuery();

            if (parkingViolations.first()) {
                System.out.println("The car has a valid permit");
                System.out.println("\n------------------------------------");
                do {
                    System.out.println();
                    int permitID = parkingViolations.getInt("PermitID");
                    String permitType = parkingViolations.getString("PermitType");
                    String startDate = parkingViolations.getString("StartDate");
                    String expirationDate = parkingViolations.getString("ExpirationDate");
                    String expirationTime = parkingViolations.getString("ExpirationTime");
                    String driverID = parkingViolations.getString("DriverID");
                    String driverStatus = parkingViolations.getString("DriverStatus");

                    System.out.println("PermitID: " + permitID);
                    System.out.println("PermitType: " + permitType);
                    System.out.println("StartDate: " + startDate);
                    System.out.println("ExpirationDate: " + expirationDate);
                    System.out.println("ExpirationTime: " + expirationTime);
                    System.out.println("SpaceType " + spaceType);
                    System.out.println("DriverID: " + driverID);
                    System.out.println("ParkingLot: " + parkingLot);
                    System.out.println("ZoneID: " + zoneID);
                    System.out.println("CarLicenseNumber: " + carLicenseNumber);
                    System.out.println("DriverStatus: " + driverStatus);

                } while (parkingViolations.next());
                System.out.println("\n------------------------------------");
                stmt.close();
                return false;
            } else {
                System.out.println("The car has no valid permit");
                stmt.close();
                return true;
            }

        } catch (SQLException err) {
            System.out.println("Error while detecting parking violations: " + err.getMessage());
        }
        return false;
    }

    public static void appealCitation() {
        String query = String.format("UPDATE Citations SET AppealStatus = '%s' WHERE CitationNo = ?", AppealStatus.IN_PROGRESS);
        int citationNo = UserInput.getInt("Enter citation number");

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            PreparedStatement stmt = DB.prepareStatement(query);
            stmt.setInt(1, citationNo);
            System.out.println("Query: " + query);

            int updateCount = stmt.executeUpdate();
            if (updateCount > 0) {
                System.out.println(String.format("Appeal generated for citation (%d)", citationNo));
            } else {
                System.out.println(String.format("Unable to generate appeal for citation (%d)", citationNo));
            }

        } catch (SQLException err) {
            System.out.println("Error while detecting parking violations: " + err.getMessage());
        }
    }

    public static void payForCitation() {
        int citationNo = UserInput.getInt("Enter citation number");
        String getCitationQuery = String.format("SELECT * FROM Citations WHERE CitationNo = %d", citationNo);
        String appealStatus = "";
        try {
            Connection DB = DatabaseConnection.getDBInstance();
            Statement stmt = DB.createStatement();
            ResultSet result = stmt.executeQuery(getCitationQuery);
            if (result.next()) {
                appealStatus = result.getString("AppealStatus");
            } else {
                throw new Exception(String.format("Citation %d does not exist", citationNo));
            }
        } catch (SQLException err) {
            System.out.println("Error while fetching citation details: " + err.getMessage());
        } catch (Exception e) {
            System.out.println(String.format("Error - %s", e.getMessage()));
        }

        String query = String.format("UPDATE Citations SET PaymentStatus = '%s', AppealStatus = '%s' WHERE CitationNo = %d;", PaymentStatus.PAID, appealStatus.equals(AppealStatus.IN_PROGRESS) ? AppealStatus.REJECT : AppealStatus.NO_APPEAL, citationNo);

        try {
            Connection DB = DatabaseConnection.getDBInstance();
            PreparedStatement stmt = DB.prepareStatement(query);
            System.out.println("Query: " + query);

            int updateCount = stmt.executeUpdate();
            if (updateCount > 0) {
                System.out.println(String.format("Payment successful for citation (%d)", citationNo));
            } else {
                System.out.println(String.format("Unable to pay for citation (%d)", citationNo));
            }

        } catch (SQLException err) {
            System.out.println("Error while paying for citations: " + err.getMessage());
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
