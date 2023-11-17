import java.sql.*;

public class WolfParkingManagementSystem {
    public static void main(String args[]) {
        try {
            DatabaseConnection.connect();
            Connection DB = DatabaseConnection.getDBInstance();
            System.out.println("\n------------------------------------------------");
            System.out.println("\n   WELCOME TO WOLF PARKING MANAGEMENT SYSTEM");
            int choice;
            do {
                System.out.println("\n------------------------------------------------");
                System.out.println("\nSelect one from the following options: ");
                System.out.println("\n1. Driver Operations" +
                        "\n2. Parking Lot Operations" +
                        "\n3. Zones Operations" +
                        "\n4. Spaces Operations" +
                        "\n5. Permit Operations " +
                        "\n6. Vehicle Operations " +
                        "\n7. Citation Operations" +
                        "\n8. Report Operations" +
                        "\n\n (Press 0 to exit the program)");
                choice = UserInput.getInt("\nEnter the option");
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        int driverChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nDrivers Operations: ");
                            System.out.println("\n1. Enter driver information" +
                                    "\n2. Update driver information" +
                                    "\n3. Delete driver" + "\n\n(Press 0 to exit to main menu)");
                            driverChoice = UserInput.getInt("\nEnter your choice");
                            DriversOperations.DriversChoice(driverChoice, DB);

                        } while (driverChoice != 0);
                        break;

                    case 2:
                        int parkingLotChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nParking Lot Operations: ");
                            System.out.println("\n1. Enter parking lot information" +
                                    "\n2. Update parking lot information" +
                                    "\n3. Delete parking lot" + "\n\n(Press 0 to exit to main menu)");
                            parkingLotChoice = UserInput.getInt("\nEnter your choice");
                            ParkingLotOperations.ParkingLotChoice(parkingLotChoice, DB);
                        } while (parkingLotChoice != 0);
                        break;

                    case 3:
                        int zonesChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nZones Operations: ");
                            System.out.println("\n1. Enter zones information/Assign Zone to Parking Lot" +
                                    "\n2. Update zones information" +
                                    "\n3. Delete zone" + "\n\n(Press 0 to exit to main menu)");
                            zonesChoice = UserInput.getInt("\nEnter your choice");
                            ZonesOperations.ZonesChoice(zonesChoice, DB);
                        } while (zonesChoice != 0);
                        break;

                    case 4:
                        int spacesChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nSpaces Operations: ");
                            System.out.println("\n1. Enter spaces information" +
                                    "\n2. Update spaces information" +
                                    "\n3. Delete space" + "\n\n(Press 0 to exit to main menu)");
                            spacesChoice = UserInput.getInt("\nEnter your choice");
                            SpacesOperations.SpacesChoice(spacesChoice, DB);
                        } while (spacesChoice != 0);
                        break;

                    case 5:
                        int permitChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nPermits Operations: ");
                            System.out.println("\n1. Enter/Assign Permit according to the Driver's status information" +
                                    "\n2. Update permit information" +
                                    "\n3. Delete permit" + "\n\n(Press 0 to exit to main menu)");
                            permitChoice = UserInput.getInt("\nEnter your choice");
                            PermitOperations.PermitsChoice(permitChoice, DB);
                        } while (permitChoice != 0);
                        break;

                    case 6:
                        int vehicleChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nVehicle Operations: ");
                            System.out.println("\n1. Enter vehicle ownership information" +
                                    "\n2. Update vehicle ownership information" +
                                    "\n3. Delete vehicle" + "\n\n(Press 0 to exit to main menu)");
                            vehicleChoice = UserInput.getInt("\nEnter your choice");
                            VehicleOperations.VehicleChoice(vehicleChoice, DB);
                        } while (vehicleChoice != 0);
                        break;

                    case 7:
                        int citationChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nCitation Operations: ");
                            System.out.println("\n1. Generate/Create Citation" +
                                    "\n2. Update Citation" +
                                    "\n3. Delete Citation" +
                                    "\n4. Detect Parking Violation" +
                                    "\n5. Appeal Citation" +
                                    "\n6. Pay For Citation" +
                                    "\n\n(Press 0 to exit to main menu)");
                            citationChoice = UserInput.getInt("\nEnter your choice");
                            Citation.CitationChoice(citationChoice);
                        } while (citationChoice != 0);
                        break;

                    case 8:
                        int reportChoice;
                        do {
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nReport Operations: ");
                            System.out.println("\n1. Generate Report for Citation" +
                                    "\n2. Total number of Citations Given In All Zones Monthly" +
                                    "\n3. Total Number Of Citations Given In All Zones Annually" +
                                    "\n4. Total Number Of Citations Given In All Zones in Specific Range Of Dates" +
                                    "\n5. List Of Zones For Each Lot" +
                                    "\n6. Number Of Cars Currently In Violation" +
                                    "\n7. Number Of Employees Having Permits For Given Parking Zone" +
                                    "\n8. Permit Information For a Given ID or Phone Number" +
                                    "\n9. Available Space Number Given A Space Type And Lot" + "\n\n(Press 0 to exit to main menu)"
                            );
                            reportChoice = UserInput.getInt("\nEnter your choice");
                            ReportOperations.ReportOperationChoice(reportChoice);
                        } while (reportChoice != 0);
                        break;

                    default:
                        System.out.println("\nInvalid Choice");
                        break;
                }

            } while (choice != 0);
        } catch (SQLException e) {
            System.out.println("Error while connecting to database - " + e.getMessage());
        } finally {
            System.out.println("\nClosing database connection ...");
            DatabaseConnection.close();
            System.out.println("\nThank you!!");
        }
    }
}
