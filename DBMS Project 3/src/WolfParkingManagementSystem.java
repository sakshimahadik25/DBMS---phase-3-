import java.sql.*;

public class WolfParkingManagementSystem {
    public static void main(String args[]) {
        try {
            DatabaseConnection.connect();
//            Connection DB = DatabaseConnection.getDBInstance();
//            String query = "SELECT * FROM ParkingLots";
//            Statement stmt = null;
//            ResultSet result = null;
//
//            try {
//                stmt = DB.createStatement();
//                result = stmt.executeQuery(query);
//                while (result.next()) {
//                    String parkingLotName = result.getString("ParkingLotName");
//                    String address = result.getString("Address");
//                    System.out.println(parkingLotName + " " + address);
//                }

                System.out.println("\n------------------------------------------------");
                System.out.println("\n---WELCOME TO WOLF PARKING MANAGEMENT SYSTEM---");
                int choice=1;
                while(choice == 1) {
                    System.out.println("\nSelect one from the following options: ");
                    System.out.println("\n1. Driver Operations" + 
                    "\n2. Parking Lot Operations" + 
                    "\n3. Zones Operations" + 
                    "\n4. Spaces Operations" + 
                    "\n5. Vehicle Operations " +
                    "\n6. Permit Operations " +
                    "\n7. Citation Operations" + 
                    "\n8. Report Operations");
                    int option=UserInput.getInt("\nEnter the option");
                    switch (option) {
                        case 1:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nDrivers Operations: ");
                            System.out.println("\n1. Enter driver information" + 
                            "\n2. Update driver information" + 
                            "\n3. Delete driver");
                            int driverChoice=UserInput.getInt("Enter your choice");
//                            DriversOperations.DriversChoice(driverChoice, DB);
                            break;

                        case 2:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nParking Lot Operations: ");
                            System.out.println("\n1. Enter parking lot information" + 
                            "\n2. Update parking lot information" + 
                            "\n3. Delete parking lot");
                            int parkingLotChoice=UserInput.getInt("\nEnter your choice: ");
                            
                            break;

                        case 3:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nZones Operations: ");
                            System.out.println("\n1. Enter zones information" + 
                            "\n2. Update zones information" + 
                            "\n3. Delete zone");
                            int zonesChoice=UserInput.getInt("\nEnter your choice: ");
                            
                            break;

                        case 4:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nSpaces Operations: ");
                            System.out.println("\n1. Enter spaces information" + 
                            "\n2. Update spaces information" + 
                            "\n3. Delete space");
                            int spacesChoice=UserInput.getInt("\nEnter your choice: ");
                            
                            break;

                        case 5:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nPermits Operations: ");
                            System.out.println("\n1. Enter/Assign Permit" + 
                            "\n2. Update permit information" + 
                            "\n3. Delete permit");
                            int permitChoice=UserInput.getInt("\nEnter your choice: ");
                            
                            break;

                        case 6:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nVehicle Operations: ");
                            System.out.println("\n1. Enter vehicle information" + 
                            "\n2. Update vehicle information" + 
                            "\n3. Delete vehicle");
                            int vehicleChoice=UserInput.getInt("\nEnter your choice: ");
                            
                            break;

                        case 7:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nReport Operations: ");
                            System.out.println("\n1. Generate/Create Citation" + 
                            "\n2. Update Citation" + 
                            "\n3. Delete Citation" + 
                            "\n4. Detect Parking Violation" + 
                            "\n5. Appeal Citation" +
                            "\n6. Pay For Citation" +
                            "\n7. Get Citation");
                            int citationChoice=UserInput.getInt("\nEnter your choice: ");
                            switch (citationChoice){
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
                                case 7:
                                    Citation.getCitation();
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                            break;

                        case 8:
                            System.out.println("\n-----------------------------------------");
                            System.out.println("\nCitation Operations: ");
                            System.out.println("\n1. Generate Report for Citation" + 
                            "\n2. Total number of Citations Given In All Zones Monthly" + 
                            "\n3. Total Number Of Citations Given In All Zones Annually" + 
                            "\n4. Total Number Of Citations Given In All Zones in Specific Range Of Dates" + 
                            "\n5. List Of Zones For Each Lot" +
                            "\n6. Number Of Cars Currently In Violation" +
                            "\n7. Number Of Employees Having Permits For Given Parking Zone" +
                            "\n8. Permit Information For a Given ID or Phone Number" +
                            "\n9. Available Space Number Given A Space Type And Lot"
                            );
                            int reportChoice=UserInput.getInt("\nEnter your choice: ");
                            
                            break;
                    
                        default:
                            break;
                    }

                    choice=UserInput.getInt("\nIf you want to continue press 1");

                }
                
//            } finally {
//                DatabaseConnection.close(result);
//                DatabaseConnection.close(stmt);
//                DatabaseConnection.close(DB);
//            }
//               catch(SQLException e){
//               System.out.println(e.getMessage());
//               }

        } catch (SQLException e) {
            System.out.println("Error while connecting to database - " + e.getMessage());
        }
    }
}
