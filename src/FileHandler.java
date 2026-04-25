import java.io.*;
import java.util.HashMap;

public class FileHandler {

    /*
     This class handles file operations for saving and loading vehicle data. 
    */

    private static final String FILE_NAME = "data/vehicles.txt";

    //------------------save to file------------------

    public static void saveToFile(HashMap<String, Vehicle> vehicles) {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            for (Vehicle v : vehicles.values()) {

                StringBuilder sb = new StringBuilder();

                sb.append(v.getOwnerName()).append(",")
                  .append(v.getVehicleNumber()).append(",")
                  .append(v.getModel());

                // Add service history
                if (!v.getServiceHistory().isEmpty()) {
                    sb.append("|");

                    for (int i = 0; i < v.getServiceHistory().size(); i++) {

                        ServiceRecord r = v.getServiceHistory().get(i);

                        sb.append(r.getDate()).append(",")
                          .append(r.getServiceType()).append(",")
                          .append(r.getCost()).append(",")
                          .append(r.getStatus());

                        if (i != v.getServiceHistory().size() - 1) {
                            sb.append(";");
                        }
                    }
                }

                writer.write(sb.toString());
                writer.write("\n");
            }

            System.out.println("Data saved to file successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    //------------------load from file------------------

    public static void loadFromFile(HashMap<String, Vehicle> vehicles) {

        vehicles.clear(); // prevent duplicate loading

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] mainParts = line.split("\\|");

                // --- VEHICLE PART ---
                String[] vehicleData = mainParts[0].split(",");

                if (vehicleData.length < 3) continue;

                String owner = vehicleData[0].trim();
                String number = vehicleData[1].trim().toUpperCase();
                String model = vehicleData[2].trim();

                Vehicle v = new Vehicle(owner, number, model);

                // --- SERVICE PART ---
                if (mainParts.length > 1) {

                    String[] services = mainParts[1].split(";");

                    for (String s : services) {

                        if (s.isEmpty()) continue;

                        String[] serviceData = s.split(",");

                        if (serviceData.length >= 4) {

                            String date = serviceData[0].trim();
                            String type = serviceData[1].trim();

                            double cost;

                            try {
                                cost = Double.parseDouble(serviceData[2].trim());
                            } catch (NumberFormatException e) {
                                continue; // skip bad data
                            }

                            String status;

                            // NEW FILE FORMAT (with status)
                            if (serviceData.length == 4) {
                                status = serviceData[3].trim();
                            } 
                            // OLD FILE FORMAT (no status)
                            else {
                                status = "Completed"; // default fallback
                            }

                            ServiceRecord record = new ServiceRecord(date, type, cost, status);
                            v.addServiceRecord(record);
                        }
                    }
                }

                vehicles.put(number, v);
            }

            System.out.println("Data loaded from file.");

        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }

}