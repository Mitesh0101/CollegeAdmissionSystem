// GovernmentCollege extends College and adds scholarship details
public class GovernmentCollege extends College {
    // ANSI escape codes for formatting terminal output in color
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";

    // Scholarship percentage offered by the government college
    double scholarship;

    // Default constructor
    public GovernmentCollege() {
    }

    // Parameterized constructor to initialize all fields including scholarship
    public GovernmentCollege(String name, String location, double fees, String course[], double cutOffPercentile[][], int seats[][], double scholarship) {
        super(name, location, fees, course, cutOffPercentile, seats);
        this.scholarship = scholarship;
    }

    // Method to validate and apply scholarship for a student based on their percentile
    void validateScholarship(Student s) {
        // Check if student is eligible for scholarship (80% or above)
        if (s.acpcPercentile >= 80) {
            System.out.println("You are egligible for the scholarship, so " + scholarship + "% of your fees has been exempted!");
            s.scholarshipGranted = true;
            // Deduct the scholarship amount from the fees to be paid
            s.feesToBePaid -= this.scholarship / 100 * fees;
        } else {
            System.out.println(RED + "Sorry, You are not eligible for scholarship!" + RESET);
        }
    }

    // Overridden method to display college details along with scholarship information
    void displayCollegeDetails() {
        super.displayCollegeDetails();
        System.out.println("Scholarship Amount Available : " + (scholarship / 100 * fees));
    }
}