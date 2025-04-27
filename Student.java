import java.util.Scanner;

// Student class representing a student with all relevant details and admission information
public class Student {
    // ANSI escape codes for formatting terminal output in color
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";

    // Flag to check if a scholarship has been granted to the student
    protected boolean scholarshipGranted = false;
    private static Scanner sc = new Scanner(System.in);

    // Student details
    protected String name;
    protected final int ACPC_ROLL_NO;
    protected double hscPercentile;
    protected double gujcetPercentile;
    protected double acpcPercentile;
    protected String studentCaste;
    protected String email;
    protected String password;
    protected int casteIndex;
    protected double feesToBePaid;
    protected College admittedInCollege;

    // Default constructor (ACPC_ROLL_NO initialized to 0)
    public Student() {
        this.ACPC_ROLL_NO = 0;
    }

    // Parameterized constructor to initialize all student details and calculate the ACPC percentile
    public Student(String name, String studentCaste, String password, int ACPC_ROLL_NO, double hscPercentile, double gujcetPercentile, int casteIndex, String email) {
        this.name = name;
        this.email = email;
        this.studentCaste = studentCaste;
        this.casteIndex = casteIndex;
        this.password = password;
        this.ACPC_ROLL_NO = ACPC_ROLL_NO;
        this.hscPercentile = hscPercentile;
        this.gujcetPercentile = gujcetPercentile;
        // ACPC percentile is a weighted average of HSC and Gujcet percentiles (60% and 40% respectively)
        this.acpcPercentile = 0.6 * hscPercentile + 0.4 * gujcetPercentile;
    }

    // Static method to create a password ensuring a minimum of 8 characters
    protected static String createPassword() {
        boolean isValid = false;
        String pass = "";
        do {
            System.out.print("Create Password : ");
            pass = sc.next();
            if (pass.length() >= 8) {
                isValid = true;
            } else {
                System.out.println(RED + "Password must have atleast 8 characters!" + RESET);
            }
        } while (!isValid);
        sc.nextLine(); // consumes new line
        return pass;
    }

    // Method to display detailed information about the student
    protected void displayStudentDetails() {
        System.out.println("==================" + name + " Details==================");
        System.out.println("Name : " + name);
        System.out.println("Email : " + email);
        System.out.println("Caste : " + studentCaste);
        System.out.println("ACPC Roll Number : " + ACPC_ROLL_NO);
        System.out.println("HSC Percentile : " + hscPercentile);
        System.out.println("Gujcet Percentile : " + gujcetPercentile);
        System.out.println("ACPC Percentile : " + acpcPercentile);
        if (admittedInCollege != null) {
            System.out.println("Admitted In : " + admittedInCollege.name);
        }
    }
}