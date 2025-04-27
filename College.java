// Main College class representing a college with basic details

public class College {
    // Static array representing the available caste categories
    static String caste[] = new String[]{"General", "EWS", "OBC"};

    // Array to hold the names of courses offered by the college.
    // Note: Initially allocated with a size of 3 but later re-initialized in setBasicCollegeDetails.
    String course[];

    // College fee amount
    double fees;

    // 2D array to hold cut-off percentiles for each course and caste category.
    // First index corresponds to the course; second index corresponds to the caste.
    double cutOffPercentile[][];

    // Name of the college
    String name;
    // Location of the college
    String location;

    // 2D array to hold the number of seats available for each course and caste category.
    // First index corresponds to the course; second index corresponds to the caste.
    int seats[][];

    // Default constructor
    public College() {
    }

    // Parameterized constructor to initialize college details
    public College(String name, String location, double fees, String course[], double cutOffPercentile[][], int seats[][]) {
        this.name = name;
        this.location = location;
        this.course = course;
        this.fees = fees;
        this.cutOffPercentile = cutOffPercentile;
        this.seats = seats;
    }

    // Method to display detailed information about the college
    void displayCollegeDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("\t" + this.name + " Details");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s: %s%n", "College Name", this.name);
        System.out.printf("%-20s: %s%n", "Location", this.location);
        System.out.printf("%-20s: %s%n", "Total Fee", this.fees);
        System.out.println("--------------------------------------------------");
        System.out.println("Cutoff Percentiles for Courses:");
        System.out.println("--------------------------------------------------");
        // Looping through each course to display its cut-off percentiles for each caste category
        for (int i = 0; i < course.length; i++) {
            System.out.println(course[i] + " :");
            for (int j = 0; j < caste.length; j++) {
                System.out.println("\t" + caste[j] + "\t : " + cutOffPercentile[i][j] + "%");
            }
            System.out.println("--------------------------------------------------");
        }
        System.out.println("Seats Available for Courses:");
        System.out.println("--------------------------------------------------");
        // Looping through each course to display available seats for each caste category
        for (int i = 0; i < course.length; i++) {
            System.out.println(course[i] + " :");
            for (int j = 0; j < caste.length; j++) {
                System.out.println("\t" + caste[j] + "\t : " + seats[i][j]);
            }
            System.out.println("--------------------------------------------------");
        }
    }
}