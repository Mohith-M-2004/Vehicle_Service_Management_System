public class ServiceRecord {

    /*
    This class represents a single service record for a vehicle, containing:
- date of service
    */

    private String date;
    private String serviceType;
    private double cost;
    private String status;

    //------------------constructor------------------

    public ServiceRecord(String date, String serviceType, double cost, String status) {
        this.date = date;
        this.serviceType = serviceType;
        this.cost = cost;
        this.status = status;
    }

//------------------getter------------------

    public String getDate() {
        return date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

//------------------setter------------------

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
