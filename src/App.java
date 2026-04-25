import java.util.*;

public class App {

//========================= MAIN METHOD ========================

/*

    This is the main method of the application. It initializes the scanner and the vehicles HashMap, loads data from file, and handles user login. 
After successful login, it displays a menu and processes user choices in a loop until the user decides to exit.
Each menu option corresponds to a specific functionality of the workshop management system, such as adding, displaying, updating, deleting vehicles, managing service records, and showing analytics.
The application ensures that data is saved to file after any modifications.

*/

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        HashMap<String, Vehicle> vehicles = new HashMap<>();
        FileHandler.loadFromFile(vehicles);

        if (!login(sc)) {
            sc.close();
            System.exit(0);
        }

        while (true) {

            System.out.println("======== SMART WORKSHOP ======== \n1. Add Vehicle \n2. Display Vehicle \n3. Update Vehicle \n4. Delete Vehicle \n5. Search Vehicle \n6. Add Service Record \n7. View Service History \n8. Show Analytics \n9. Exit \n================================\n");
            System.out.print("Enter your choice: ");
            int choice;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine(); // clear buffer
                continue;
            }
            sc.nextLine();
           

            switch (choice) {
                case 1://Add Vehicle
                    addVehicle(vehicles, sc);
                    FileHandler.saveToFile(vehicles);
                    break;

                case 2://Display Vehicles
                    
                    displayVehicles(vehicles,sc);
                    break;

                case 3://Update Vehicle
                    updateVehicle(vehicles, sc);
                    FileHandler.saveToFile(vehicles);
                    break;

                case 4://Delete Vehicle
                    deleteVehicle(vehicles, sc);
                    FileHandler.saveToFile(vehicles);
                    break;

                case 5:
                    searchVehicle(vehicles, sc);
                    break;
                
                case 6:
                    addServiceRecord(vehicles, sc);
                    FileHandler.saveToFile(vehicles);
                    break;

                case 7:
                    viewServiceHistory(vehicles, sc);
                    break;
                
                case 8:
                    showAnalytics(vehicles);
                    break;

                case 9://Exit
                    FileHandler.saveToFile(vehicles);
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;
            
                default://handle invalid inputs
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }

//========================= VALIDATION METHODS ========================

// This section contains methods for validating user input, such as login credentials and integer inputs for menu choices and vehicle details.

    //========================= LOGIN METHOD ========================

    /* 

    This method handles the user login process, checking credentials against predefined values.
    It allows up to 3 attempts before exiting the application. 
    
    */

    public static boolean login(Scanner sc) {

        final String USERNAME = "Admin";
        final String PASSWORD = "1234";

        System.out.println("======== LOGIN ========");

        for (int i = 0; i < 3; i++) {

            System.out.print("Enter username: ");
            String user = sc.nextLine();

            System.out.print("Enter password: ");
            String pass = sc.nextLine();

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                System.out.println("Login successful!\n");
                return true;
            } else {
                System.out.println("Invalid credentials. Attempts left: " + (2 - i));
            }
        }

        System.out.println("Too many failed attempts. Exiting...");
        return false;
    }

    //========================= INPUT VALIDATION METHOD ========================

    /*

    This method prompts the user for an integer input and validates it.
    It ensures that the input is a number and greater than 0, providing error messages for invalid inputs and allowing the user to try again until a valid input is received.
    
    */

    public static int getValidInt(Scanner sc, String message) {
        int value;

        while (true) {
            System.out.print(message);

            try {
                value = sc.nextInt();
                sc.nextLine();

                if (value <= 0) {
                    System.out.println("Enter a number greater than 0.");
                    continue;
                }

                return value;

            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine();
            }
        }
    }

//========================= VEHICLE MANAGEMENT METHODS ========================

/*

This section contains methods for managing vehicle details, including adding new vehicles, displaying existing vehicles, updating vehicle information, deleting vehicles, and searching for vehicles based on owner name.
Each method interacts with the vehicles HashMap to perform the necessary operations and ensures that user input is validated and handled appropriately.

*/

    //========================= ADD VEHICLE METHOD ========================

    /*

    This method allows the user to add new vehicle details to the system.
    It prompts the user for the number of vehicles to add and then collects details for each vehicle, including owner name, vehicle number, and model.
    The method ensures that the owner name and model are not empty, and that the vehicle number is unique and not empty.
    Once the details are collected and validated, the new vehicle is added to the vehicles HashMap, and a success message is displayed after all vehicles have been added.
    
    */

    public static void addVehicle(HashMap<String, Vehicle> vehicles, Scanner sc){// This method is used to add new vehicle details
        
        System.out.println("======== ADD VEHICLE ========\n");
            int n = getValidInt(sc, "Enter number of vehicles: ");
            sc.nextLine(); // consume newline

            for (int i = 0; i < n; i++) {
                System.out.println("\n--- Enter details for Vehicle " + (i + 1) + " ---");

                System.out.print("Enter owner name: ");
                String ownerName = sc.nextLine();
                ownerName = ownerName.trim();

                while (ownerName.isEmpty()) {
                    System.out.println("Owner name cannot be empty. Please enter a valid owner name.");
                    System.out.print("Enter owner name: ");
                    ownerName = sc.nextLine();
                    ownerName = ownerName.trim();
                }

                System.out.print("Enter vehicle number: ");
                String vehicleNumber = sc.nextLine();
                vehicleNumber=vehicleNumber.trim().toUpperCase();

                while(vehicleNumber.isEmpty()) {
                    System.out.println("Vehicle number cannot be empty. Please enter a valid vehicle number.");
                    System.out.print("Enter vehicle number: ");
                    vehicleNumber = sc.nextLine();
                    vehicleNumber=vehicleNumber.trim().toUpperCase();
                }

                while (vehicles.containsKey(vehicleNumber)) {
                    System.out.println("Vehicle number already exists. Please enter a unique vehicle number.");
                    System.out.print("Enter vehicle number: ");
                    vehicleNumber = sc.nextLine();
                    vehicleNumber=vehicleNumber.trim().toUpperCase();
                }
                System.out.print("Enter model: ");
                String model = sc.nextLine();
                model = model.trim();

                while(model.isEmpty()){
                    System.out.println("Model cannot be empty. Please enter a valid model.");
                    System.out.print("Enter model: ");
                    model = sc.nextLine();
                    model = model.trim();
                }

                Vehicle v = new Vehicle(ownerName, vehicleNumber, model);
                vehicles.put(vehicleNumber, v);
                
            }
                System.out.println(n+" vehicle(s) added successfully.");

    }

    //========================= DISPLAY VEHICLE METHOD ========================

    /*

    This method displays the list of vehicles in the system. 
    It first checks if there are any vehicles to display and prompts the user to choose a display option: normal display, sort by owner name, or sort by vehicle number.
    Based on the user's choice, it sorts the list of vehicles accordingly and then prints the details in a tabular format using the printTable method. 
    Finally, it shows the total number of vehicles displayed.
    
    */

   public static void displayVehicles(HashMap<String, Vehicle> vehicles, Scanner sc) {

    System.out.println("======== Display Vehicle =========\n");

    if (vehicles.isEmpty()) {
        System.out.println("No vehicles to display.");
        return;
    }
    System.out.println("\nChoose display option:");
    System.out.println("1. Normal Display");
    System.out.println("2. Sort by Owner Name");
    System.out.println("3. Sort by Vehicle Number");

    int option ;

    try {
        option = sc.nextInt();
    } catch (Exception e) {
        System.out.println("Invalid input. Defaulting to normal display.");
        sc.nextLine(); // clear buffer
        option = 1;
    }

    ArrayList<Vehicle> list = new ArrayList<>(vehicles.values());

    switch (option) {

        case 1:
            System.out.println("\n--- Vehicle List ---");
            printTable(list);
            System.out.println("Total Vehicles: " + list.size());
            System.out.println("--------------------------\n");
            break;

        case 2:
            list.sort((v1, v2) ->
                v1.getOwnerName().compareToIgnoreCase(v2.getOwnerName())
            );

            System.out.println("\n--- Sorted by Owner Name ---");
            printTable(list);
            System.out.println("Total Vehicles: " + list.size());
            System.out.println("--------------------------\n");
            break;

        case 3:
            list.sort((v1, v2) ->
                v1.getVehicleNumber().compareToIgnoreCase(v2.getVehicleNumber())
            );

            System.out.println("\n--- Sorted by Vehicle Number ---");
            printTable(list);
            System.out.println("Total Vehicles: " + list.size());
            System.out.println("--------------------------\n");
            break;

        default:
            System.out.println("Invalid option.");
        }
    }

    //========================= PRINT TABLE METHOD ========================

    /*

    This method takes a list of vehicles and prints their details in a formatted table.
    It uses printf to align the columns for vehicle number, owner name, and model, and includes headers and separators for better readability.
    
    */

    public static void printTable(ArrayList<Vehicle> list) {

        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("| %-3s | %-15s | %-18s | %-25s |\n",
                "No", "Owner Name", "Vehicle Number", "Model");
        System.out.println("-----------------------------------------------------------------------");

        int i = 1;
        for (Vehicle v : list) {
            System.out.printf("| %-3d | %-15s | %-18s | %-25s |\n",
                    i++,
                    v.getOwnerName(),
                    v.getVehicleNumber(),
                    v.getModel());
        }

        System.out.println("-----------------------------------------------------------------------\n");
    }

    //========================= UPDATE VEHICLE METHOD ========================

    /*

    This method allows the user to update the details of an existing vehicle.
    It prompts the user to enter the vehicle number they wish to update and checks if the vehicle exists in the system. 
    If found, it displays the current details of the vehicle and allows the user to enter new values for the owner name and model.
    The user can choose to leave the input blank to keep the current value. 
    After updating the details, a success message is displayed. If the vehicle is not found, an appropriate message is shown.
    
    */

    public static void updateVehicle(HashMap<String, Vehicle> vehicles, Scanner sc) {//This method is used to update vehicle details
        
        System.out.println("======== UPDATE VEHICLE ========\n");

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }

        System.out.println("Enter vehicle number to update: ");
        String searchNumber = sc.nextLine();
        searchNumber=searchNumber.trim().toUpperCase();
        
        Vehicle v = vehicles.get(searchNumber);
        if(v == null){
            System.out.println("Vehicle not found.");
            return;
            
        }
        System.out.println("\n--- Vehicle Found ---");
        System.out.println("Owner Name: " + v.getOwnerName());
        System.out.println("Vehicle Number: " + v.getVehicleNumber());
        System.out.println("Model: " + v.getModel());
        System.out.println("-----------------------");

        System.out.print("Enter new owner name (leave blank to keep current): ");
        String newOwnerName = sc.nextLine();
        newOwnerName = newOwnerName.trim();

        if (!newOwnerName.isEmpty()) {
            v.setOwnerName(newOwnerName);
        }

        System.out.print("Enter new model (leave blank to keep current): ");
        String newModel = sc.nextLine();
        newModel = newModel.trim();

        if (!newModel.isEmpty()) {
            v.setModel(newModel);
        }

        System.out.println("Vehicle updated successfully.");
        
    }

    //========================= DELETE VEHICLE METHOD ========================

    /*

    This method allows the user to delete a vehicle from the system. 
    It prompts the user to enter the vehicle number they wish to delete and checks if the vehicle exists in the system. 
    If found, it displays the details of the vehicle and asks for confirmation before deletion. 
    If the user confirms, the vehicle is removed from the vehicles HashMap, and a success message is displayed. 
    If the vehicle is not found or if the user cancels the deletion, appropriate messages are shown.
    
    */

    public static void deleteVehicle(HashMap<String, Vehicle> vehicles, Scanner sc){//This method is used to delete vehicle details
        
        System.out.println("======== DELETE VEHICLE ========\n");

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }
        System.out.println("Enter vehicle number to delete: ");
        String deleteNumber = sc.nextLine();
        deleteNumber=deleteNumber.trim().toUpperCase();

        Vehicle current = vehicles.get(deleteNumber);
        if(current == null){
            System.out.println("Vehicle not found.");
            return;
        }
        System.out.println("\n--- Vehicle Found ---");
        System.out.println("Owner Name: " + current.getOwnerName());
        System.out.println("Vehicle Number: " + current.getVehicleNumber());
        System.out.println("Model: " + current.getModel());
        System.out.println("-----------------------");

        System.out.println("Are you sure you want to delete this vehicle? (yes/no): ");
        String confirmation = sc.nextLine();

        if (confirmation.trim().equalsIgnoreCase("yes")) {
            vehicles.remove(deleteNumber);
            System.out.println("Vehicle deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }

    }

    //========================= SEARCH VEHICLE METHOD ========================

    /*

    This method allows the user to search for vehicles based on the owner's name.
    It prompts the user to enter a name or part of a name and then iterates through the vehicles HashMap to find matches.
    If a match is found, the vehicle details are displayed. If no matches are found, an appropriate message is shown to the user.
    
    */

    public static void searchVehicle(HashMap<String, Vehicle> vehicles, Scanner sc) {

        System.out.println("======== SEARCH VEHICLE ========\n");
        System.out.print("Enter owner name to search: ");
        String name = sc.nextLine().toLowerCase().trim();

        boolean found = false;

        for (Vehicle v : vehicles.values()) {
            if (v.getOwnerName().toLowerCase().contains(name)) {
                System.out.println(v);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching vehicles found.");
        }
    }

    //========================= ADD SERVICE RECORD METHOD ========================

    /*

    This method allows the user to add a service record for a specific vehicle.
    It prompts the user to enter the vehicle number and then collects details about the service record.
    The service record is then added to the vehicle's service history.
    
    */

    public static void addServiceRecord(HashMap<String, Vehicle> vehicles, Scanner sc) {

        System.out.println("======== ADD SERVICE RECORD ========\n");

        System.out.print("Enter vehicle number: ");
        String number = sc.nextLine().trim().toUpperCase();

        Vehicle v = vehicles.get(number);

        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter service date: ");
        String date = sc.nextLine();

        System.out.print("Enter service type: ");
        String type = sc.nextLine();

        System.out.print("Enter cost: ");
        double cost = sc.nextDouble();
        sc.nextLine(); // clear buffer

        String status = "";

        while (true) {
            System.out.println("Select Service Status:");
            System.out.println("1. Pending");
            System.out.println("2. In Progress");
            System.out.println("3. Completed");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    status = "Pending";
                    break;
                case "2":
                    status = "In Progress";
                    break;
                case "3":
                    status = "Completed";
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    continue;
            }
            break;
        }

        ServiceRecord record = new ServiceRecord(date, type, cost, status);
        v.addServiceRecord(record);

        System.out.println("Service record added successfully.");
    }

    //========================= VIEW SERVICE HISTORY METHOD ========================

    /*

    This method allows the user to view the service history of a specific vehicle.
    It prompts the user to enter the vehicle number and checks if the vehicle exists in the system.
    If the vehicle is found, it checks if there are any service records available. 
    If there are records, it displays them in a formatted table along with the total amount spent on services for that vehicle.
    If the vehicle is not found or if there are no service records, appropriate messages are displayed to the user.
    
    */  

    public static void viewServiceHistory(HashMap<String, Vehicle> vehicles, Scanner sc) {

        System.out.println("======== VIEW SERVICE HISTORY ========\n");
        System.out.print("Enter vehicle number: ");
        String number = sc.nextLine().trim().toUpperCase();

        Vehicle v = vehicles.get(number);

        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (v.getServiceHistory().isEmpty()) {
            System.out.println("No service history available.");
            return;
        }

        System.out.println("\n--- Service History ---");

        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-12s | %-25s | %-8s | %-12s |\n",
                "No", "Date", "Service Type", "Cost", "Status");
        System.out.println("---------------------------------------------------------------------------");

        int i = 1;
        for (ServiceRecord r : v.getServiceHistory()) {
            System.out.printf("| %-3d | %-12s | %-25s | Rs. %-6.2f | %-12s |\n",
                    i++,
                    r.getDate(),
                    r.getServiceType(),
                    r.getCost(),
                    r.getStatus());
                    
        }

        System.out.println("---------------------------------------------------------------------------\n");

        double total = 0;
        for (ServiceRecord r : v.getServiceHistory()) {
            total += r.getCost();
        }
        System.out.printf("Total Spent: Rs. %.2f\n", total);

    }

    //========================= SHOW ANALYTICS METHOD ========================

    /*

    This method displays analytics about the service records in the system.
    It calculates and shows the total number of vehicles, services, and revenue.
    It also identifies the most serviced vehicle and the highest spending vehicle.

    */

    public static void showAnalytics(HashMap<String, Vehicle> vehicles) {

        System.out.println("======== SHOW ANALYTICS ========\n");

    if (vehicles.isEmpty()) {
        System.out.println("No data available.");
        return;
    }

    int totalVehicles = vehicles.size();
    int totalServices = 0;
    double totalRevenue = 0;

    double maxSpent = 0;
    String highestSpender = "";

    String topVehicleNumber = "";
    int maxServices = 0;
    int completed = 0;
    int pending = 0;
    int inProgress = 0;

    for (Vehicle v : vehicles.values()) {

        int serviceCount = v.getServiceHistory().size();
        totalServices += serviceCount;

        double vehicleTotal = 0;

        for (ServiceRecord r : v.getServiceHistory()) {
            totalRevenue += r.getCost();
            vehicleTotal += r.getCost();

            String status = r.getStatus();

            if (status != null) {
                switch (status.toLowerCase()) {
                    case "completed": completed++; break;
                    case "pending": pending++; break;
                    case "in progress": inProgress++; break;
                }
            }
        }

        // Find most serviced vehicle
        if (serviceCount > maxServices) {
            maxServices = serviceCount;
            topVehicleNumber = v.getVehicleNumber();
        }

        if (vehicleTotal > maxSpent) {// Find highest spender
        maxSpent = vehicleTotal;
        highestSpender = v.getVehicleNumber();
}
    }
    

    System.out.println("\n========= ANALYTICS DASHBOARD =========");

    System.out.println("Total Vehicles        : " + totalVehicles);
    System.out.println("Total Services        : " + totalServices);
    System.out.println("Total Revenue         : Rs. " + totalRevenue);

    if (!topVehicleNumber.isEmpty()) {
        System.out.println("Most Serviced Vehicle : " + topVehicleNumber +
                " (" + maxServices + " services)");
    }

    if (!highestSpender.isEmpty()) {
        System.out.println("Highest Spending Vehicle : " + highestSpender + " (Rs. " + maxSpent + ")");
    }

    System.out.println("Completed Services : " + completed);
    System.out.println("Pending Services   : " + pending);
    System.out.println("In Progress        : " + inProgress);

    System.out.println("========================================\n");
}
   
//========================= END OF CLASS ========================


}

