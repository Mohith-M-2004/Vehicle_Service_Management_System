import java.util.ArrayList;

public class Vehicle {

    /*
    This class represents a vehicle with its details and service history. It contains:
    - ownerName: the name of the vehicle owner
    - vehicleNumber: the number of the vehicle
    - model: the model of the vehicle
    */

    private String ownerName;
    private String vehicleNumber;
    private String model;
    private ArrayList<ServiceRecord> serviceHistory;

    public Vehicle(String ownerName, String vehicleNumber, String model) {//constructor to initialize vehicle details
        this.ownerName = ownerName;
        this.vehicleNumber = vehicleNumber;
        this.model = model;
        this.serviceHistory = new ArrayList<>();
    }

//-------------getter--------------------

    public String getOwnerName() {
        return ownerName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getModel() {
        return model;
    }

//-----------------------setter-----------------------

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    //------------------toString------------------

    @Override
    public String toString() {
        return "Owner Name: " + ownerName + "\n" +
                "Vehicle Number: " + vehicleNumber + "\n" +
                "Model: " + model + "\n" + "-----------------------";
    }

    //------------------service history------------------

    public void addServiceRecord(ServiceRecord record) {
        serviceHistory.add(record);
    }

    public ArrayList<ServiceRecord> getServiceHistory() {
        return serviceHistory;
    }
}
