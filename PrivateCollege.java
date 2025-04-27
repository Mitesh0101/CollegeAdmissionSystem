// PrivateCollege extends College and adds management quota details
public class PrivateCollege extends College {
    // Number of management quota seats available in the private college
    int managementQuotaSeats;

    // Default constructor
    public PrivateCollege() {
    }

    // Parameterized constructor to initialize all fields including management quota seats
    public PrivateCollege(String name, String location, double fees, String course[], double cutOffPercentile[][], int seats[][], int managementQuotaSeats) {
        super(name, location, fees, course, cutOffPercentile, seats);
        this.managementQuotaSeats = managementQuotaSeats;
    }

    // Overridden method to display college details along with management quota information
    void displayCollegeDetails() {
        super.displayCollegeDetails();
        System.out.println("Management Quota Seats : " + managementQuotaSeats);
    }
}