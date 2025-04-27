import java.util.*;

// Main system class for handling college admission operations
public class CollegeAdmissionSystem {
    // ANSI escape codes for formatting terminal output in color
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private final Scanner sc = new Scanner(System.in);

    // List to store all registered colleges
    static ArrayList<College> listOfColleges = new ArrayList<>();
    // List to store all registered students
    static ArrayList<Student> listOfStudents = new ArrayList<>();

    // Utility method to validate if input string is "yes" or "no"
    private boolean validateYN(String yn) {
        if (yn.equalsIgnoreCase("yes") || yn.equalsIgnoreCase("no")) {
            return true;
        } else {
            System.out.println(RED + "Enter yes or no!" + RESET);
            return false;
        }
    }

    // Method to set basic details for a college by taking input from the user
    private void setBasicCollegeDetails(College college) {
        while (college.name == null) {
            System.out.print("Enter Name : ");
            String name = sc.nextLine();
            boolean found = false;
            for (College college1 : listOfColleges) {
                if (college1.name.equalsIgnoreCase(name)) {
                    System.out.println(RED + "A College already exists with this name! Please Enter New Name!" + RESET);
                    found = true;
                }
            }
            if (!found) {
                college.name = name;
            }
        }

        System.out.print("Enter Location : ");
        college.location = sc.nextLine();

        // Ensure that the fees entered is not negative
        do {
            System.out.print("Enter Fees : ");
            try {
                college.fees = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears the bad input from scanner
                continue;
            }
            if (college.fees < 0) {
                System.out.println(RED + "Fees cannot be negative!" + RESET);
            }
        } while (college.fees < 0);

        // Input the number of courses available and update the courses array
        int courseSize = -1;
        while (courseSize<0) {
            System.out.print("Enter the number of courses available at your college : ");
            try {
                courseSize = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input" + RESET);
                sc.nextLine(); // Clears the bad input from scanner
            }
        }
        sc.nextLine(); // consumes newline
        college.course = new String[courseSize];
        college.cutOffPercentile = new double[college.course.length][College.caste.length];
        college.seats = new int[college.course.length][College.caste.length];
        for (int i = 0; i < courseSize; i++) {
            System.out.print("Enter course " + (i + 1) + " name : ");
            college.course[i] = sc.nextLine();
        }

        // Taking and Validating Cut Off Percentiles for each course and caste
        System.out.println("Enter Cut-Off Percentiles for Different Categories and Branches as Prompted : ");
        for (int i = 0; i < college.course.length; i++) {
            System.out.println("Enter CutOff Percentiles for " + college.course[i] + " Branch : ");
            for (int j = 0; j < College.caste.length; j++) {
                double testPercentile = -1;
                while (testPercentile > 100 || testPercentile < 0) {
                    System.out.print(College.caste[j] + " : ");
                    try {
                        testPercentile = sc.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println(RED + "Invalid Input!" + RESET);
                        sc.nextLine(); // Clears the bad input from scanner
                        continue;
                    }
                    if (testPercentile > 100 || testPercentile < 0) {
                        System.out.println(RED + "Percentile must be between 0 and 100!" + RESET);
                    }
                }
                college.cutOffPercentile[i][j] = testPercentile;
            }
        }

        // Taking input for number of seats available for each course and caste
        System.out.println("Enter number of seats for Different Categories and Branches as Prompted : ");
        for (int i = 0; i < college.course.length; i++) {
            System.out.println("Enter number of seats for " + college.course[i] + " Branch : ");
            for (int j = 0; j < College.caste.length; j++) {
                boolean isValid = false;
                while (!isValid) {
                    System.out.print(College.caste[j] + " : ");
                    try {
                        college.seats[i][j] = sc.nextInt();
                        isValid = true;
                    } catch (InputMismatchException e) {
                        System.out.println(RED + "Invalid Input!" + RESET);
                        sc.nextLine(); // Clears the bad input from scanner
                    }
                }
            }
        }
        sc.nextLine(); // Consumes NewLine
    }

    // Method to add a new college (either Government or Private) to the list
    void addCollege() {
        System.out.println("==================COLLEGE REGISTER MENU==================");
        System.out.println("Press 1 to add Government College");
        System.out.println("Press 2 to add Private College");
        int choice = 0;
        while (choice<1 || choice>2) {
            System.out.print("Enter your choice : ");
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears the bad input from scanner
                continue;
            }
            if (choice<1 || choice>2) {
                System.out.println(RED + "Invalid Choice!" + RESET);
            }
        }
        sc.nextLine(); // Consumes NewLine

        switch (choice) {
            case 1:
                // Creating and adding a new Government College
                GovernmentCollege gvt = new GovernmentCollege();
                setBasicCollegeDetails(gvt);
                double scholarship = -1;
                while (scholarship<0 || scholarship>100) {
                    System.out.print("Enter Scholarship Percentage : ");
                    try {
                        scholarship = sc.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println(RED + "Invalid Input!" + RESET);
                        sc.nextLine(); // Clears bad input from Scanner
                        continue;
                    }
                    if (scholarship<0 || scholarship>100) {
                        System.out.println(RED + "Percentage should be between 0 and 100!" + RESET);
                    }
                }
                gvt.scholarship = scholarship;
                sc.nextLine(); // Consumes NewLine
                listOfColleges.add(gvt);
                System.out.println("College Added Successfully!");
                break;

            case 2:
                // Creating and adding a new Private College
                PrivateCollege pvt = new PrivateCollege();
                setBasicCollegeDetails(pvt);
                int managementQuotaSeats = -1;
                while (managementQuotaSeats<0) {
                    System.out.print("Enter number of Management Quota Seats : ");
                    try {
                        managementQuotaSeats = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println(RED + "Invalid Input!" + RESET);
                        sc.nextLine(); // Clears bad input from scanner
                        continue;
                    }
                    if (managementQuotaSeats<0) {
                        System.out.println(RED + "Management Quota Seats can't be negative!" + RESET);
                    }
                }
                pvt.managementQuotaSeats = managementQuotaSeats;
                sc.nextLine(); // Consumes NewLine
                listOfColleges.add(pvt);
                System.out.println("College Added Successfully!");
                break;
        }
    }

    // Method to search for colleges by name or location
    void searchCollege() {
        System.out.println("==================COLLEGE SEARCH MENU==================");
        System.out.println("Press 1 to search by Name");
        System.out.println("Press 2 to search by Location");
        int choice = 0;
        while (choice<1 || choice>2) {
            System.out.print("Enter your choice : ");
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears the bad input from scanner
                continue;
            }
            if (choice<1 || choice>2) {
                System.out.println(RED + "Invalid Choice!" + RESET);
            }
        }
        sc.nextLine(); // Consumes NewLine

        boolean found = false;

        switch (choice) {
            case 1:
                // Search colleges by name
                System.out.print("Enter College Name: ");
                String nameSearch = sc.nextLine();
                System.out.println("-------------------------------------------------------------------------------------------------");
                System.out.printf("%-50s %-20s %-10s \n", "College Name", "Location", "Fees");
                System.out.println("-------------------------------------------------------------------------------------------------");
                for (College college : listOfColleges) {
                    if (college.name.contains(nameSearch)) {
                        System.out.printf("%-50s %-20s %.2f \n", college.name, college.location, college.fees);
                        found = true;
                    }
                }
                System.out.println("-------------------------------------------------------------------------------------------------");
                break;

            case 2:
                // Search colleges by location
                System.out.print("Enter College Location: ");
                String locationSearch = sc.nextLine();
                System.out.println("-------------------------------------------------------------------------------------------------");
                System.out.printf("%-50s %-20s %-10s \n", "College Name", "Location", "Fees");
                System.out.println("-------------------------------------------------------------------------------------------------");

                for (College college : listOfColleges) {
                    if (college.location.contains(locationSearch)) {
                        System.out.printf("%-50s %-20s %.2f \n", college.name, college.location, college.fees);
                        found = true;
                    }
                }
                System.out.println("-------------------------------------------------------------------------------------------------");
                break;
        }
        if (!found) {
            System.out.println(RED + "No colleges found with the given details." + RESET);
        }
    }

    // Method to update the details of an existing college
    void updateCollege() {
        System.out.print("Enter the name of the college you want to update: ");
        String nameSearch = sc.nextLine();
        boolean found = false;
        for (College college : listOfColleges) {
            if (college.name.equalsIgnoreCase(nameSearch)) {
                found = true;

                // Update college name if user desires
                while (true) {
                    System.out.print("Do you want to update the name? (yes/no) : ");
                    String yn = sc.next();
                    sc.nextLine();
                    if (validateYN(yn)) {
                        if (yn.equalsIgnoreCase("yes")) {
                            System.out.print("Enter new name : ");
                            college.name = sc.nextLine();
                        }
                        break;
                    }
                }

                // Update college location if user desires
                while (true) {
                    System.out.print("Do you want to update the location? (yes/no) : ");
                    String yn = sc.next();
                    sc.nextLine();
                    if (validateYN(yn)) {
                        if (yn.equalsIgnoreCase("yes")) {
                            System.out.print("Enter new location : ");
                            college.location = sc.nextLine();
                        }
                        break;
                    }
                }

                // Update college fees if user desires
                while (true) {
                    System.out.print("Do you want to update the fees? (yes/no) : ");
                    String yn = sc.next();
                    sc.nextLine();
                    if (validateYN(yn)) {
                        if (yn.equalsIgnoreCase("yes")) {
                            double fees = -1;
                            while (fees<0) {
                                System.out.print("Enter new fees : ");
                                try {
                                    fees = sc.nextDouble();
                                } catch (InputMismatchException e) {
                                    System.out.println(RED + "Invalid Input!" + RESET);
                                    sc.nextLine(); // Clears bad input from scanner404
                                    continue;
                                }
                                if (fees<0) {
                                    System.out.println(RED + "Fees cannot be negative!" + RESET);
                                }
                            }
                            college.fees = fees;
                            sc.nextLine(); // Consume newline
                        }
                        break;
                    }
                }

                // Update scholarship percentage if the college is a GovernmentCollege
                if (college instanceof GovernmentCollege gvt) { // gvt is a pattern variable
                    while (true) {
                        System.out.print("Do you want to update the scholarship percentage? (yes/no) : ");
                        String yn = sc.next();
                        sc.nextLine(); // Consumes NewLine
                        if (validateYN(yn)) {
                            if (yn.equalsIgnoreCase("yes")) {
                                double scholarship=-1;
                                while (scholarship<0 || scholarship>100) {
                                    System.out.print("Enter new scholarship percentage: ");
                                    try {
                                        scholarship = sc.nextDouble();
                                    } catch (InputMismatchException e) {
                                        System.out.println(RED + "Invalid Input!" + RESET);
                                        sc.nextLine(); // Clears bad input from scanner
                                        continue;
                                    }
                                    if (scholarship<0 || scholarship>100) {
                                        System.out.println(RED + "Scholarship percentage should be between 0 and 100!" + RESET);
                                    }
                                }
                                gvt.scholarship = scholarship;
                                sc.nextLine(); // Consume newline
                            }
                            break;
                        }
                    }
                }

                // Update management quota seats if the college is a PrivateCollege
                else if (college instanceof PrivateCollege pvt) { // pvt is a pattern variable
                    while (true) {
                        System.out.print("Do you want to update management quota seats? (yes/no) : ");
                        String yn = sc.next();
                        sc.nextLine();
                        if (validateYN(yn)) {
                            if (yn.equalsIgnoreCase("yes")) {
                                int managementQuotaSeats = -1;
                                while (managementQuotaSeats<0) {
                                    System.out.print("Enter new number of management quota seats: ");
                                    try {
                                        managementQuotaSeats = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println(RED + "Invalid Input!" + RESET);
                                        sc.nextLine(); // Clears bad input from scanner
                                        continue;
                                    }
                                    if (managementQuotaSeats<0) {
                                        System.out.println(RED + "Management Quota Seats cannot be negative!" + RESET);
                                    }
                                }
                                pvt.managementQuotaSeats = managementQuotaSeats;
                                sc.nextLine();
                            }
                            break;
                        }
                    }
                }

                System.out.println("College details updated successfully!");
                college.displayCollegeDetails();
                break;
            }
        }
        if (!found) {
            System.out.println(RED + "No college found with the given name." + RESET);
        }
    }

    // Method to remove a college from the list based on its name
    void removeCollege() {
        System.out.print("Enter the name of the college you want to remove: ");
        String nameSearch = sc.nextLine();
        boolean found = false;
        for (int i = 0; i < listOfColleges.size(); i++) {
            if (listOfColleges.get(i).name.equalsIgnoreCase(nameSearch)) {
                found = true;
                listOfColleges.remove(i);
                System.out.println("College removed successfully!");
                break;
            }
        }
        if (!found) {
            System.out.println(RED + "No college found with the given name." + RESET);
        }
    }

    // Method to display all colleges in a tabular format
    void displayAllColleges() {
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-50s %-20s %-10s \n", "College Name", "Location", "Fees");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (College college : listOfColleges) {
            System.out.printf("%-50s %-20s %.2f \n", college.name, college.location, college.fees);
        }

        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    // Method to register a new student in the system
    void registerStudent() {
        System.out.print("Enter Name : ");
        String name = sc.nextLine();
        int ACPC_ROLL_NO = -1;
        while (ACPC_ROLL_NO<100000 || ACPC_ROLL_NO>999999) {
            System.out.print("Enter ACPC Roll Number : ");
            try {
                ACPC_ROLL_NO = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears bad input from scanner;
                continue;
            }
            if (ACPC_ROLL_NO<100000 || ACPC_ROLL_NO>999999) {
                System.out.println(RED + "ACPC Roll Number should be 6 digits long!" + RESET);
            }
        }

        // Checking for Duplicate Roll Numbers to prevent re-registration
        for (Student student : listOfStudents) {
            if (student.ACPC_ROLL_NO == ACPC_ROLL_NO) {
                System.out.println(RED + "You are already registered, Please Login!" + RESET);
                sc.nextLine(); // Consumes Newline
                return;
            }
        }

        // Validating Email by ensuring it contains "@" and ".com"
        String email;
        do {
            System.out.print("Enter Email : ");
            email = sc.next();
            if (!email.contains("@") || !email.contains(".com")) {
                System.out.println(RED + "Invalid Email!" + RESET);
            }
        } while (!email.contains("@") || !email.contains(".com"));

        // Validating HSC Percentile ensuring it is between 0 and 100
        double hscPercentile = -1;
        do {
            System.out.print("Enter HSC Percentile : ");
            try {
                hscPercentile = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears bad input from scanner
                continue;
            }
            if (hscPercentile > 100 || hscPercentile < 0) {
                System.out.println(RED + "HSC Percentile must be between 0 and 100" + RESET);
            }
        } while (hscPercentile > 100 || hscPercentile < 0);

        // Validating Gujcet Percentile ensuring it is between 0 and 100
        double gujcetPercentile = -1;
        do {
            System.out.print("Enter Gujcet Percentile : ");
            try {
                gujcetPercentile = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine();
                continue;
            }
            if (gujcetPercentile > 100 || gujcetPercentile < 0) {
                System.out.println(RED + "Gujcet Percentile must be between 0 and 100" + RESET);
            }
        } while (gujcetPercentile > 100 || gujcetPercentile < 0);

        // Validating Caste input by providing options for General, EWS, and OBC
        System.out.println("Enter Caste : ");
        System.out.println("Press 1 for General");
        System.out.println("Press 2 for EWS");
        System.out.println("Press 3 for OBC");
        int choice = -1;
        String studentCaste = "";
        int casteIndex = -1;
        do {
            System.out.print("Enter Choice : ");
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears bad input from scanner
                continue;
            }
            switch (choice) {
                case 1:
                    studentCaste = "General";
                    casteIndex = 0;
                    break;

                case 2:
                    studentCaste = "EWS";
                    casteIndex = 1;
                    break;

                case 3:
                    studentCaste = "OBC";
                    casteIndex = 2;
                    break;

                default:
                    System.out.println(RED + "Invalid Choice!" + RESET);
                    choice = 404;
                    break;
            }
        } while (choice<1 || choice>3);
        sc.nextLine(); // Consumes Newline
        // Create a password for the student account using the helper method
        String password = Student.createPassword();
        // Add the new student to the list of registered students
        listOfStudents.add(new Student(name, studentCaste, password, ACPC_ROLL_NO, hscPercentile, gujcetPercentile, casteIndex, email));
    }

    // Method to handle student login including password
    int loginStudent() {
        int loginACPCRollNo = -1;
        while (loginACPCRollNo <100000 || loginACPCRollNo >999999) {
            System.out.print("Enter Your ACPC Roll Number : ");
            try {
                loginACPCRollNo = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears bad input from scanner;
                continue;
            }
            if (loginACPCRollNo <100000 || loginACPCRollNo >999999) {
                System.out.println(RED + "ACPC Roll Number should be 6 digits long!" + RESET);
            }
        }

        boolean found = false;
        int count = 0;
        for (int i = 0; i < listOfStudents.size(); i++) {
            if (listOfStudents.get(i).ACPC_ROLL_NO == loginACPCRollNo) {
                found = true;
                while (count < 3) {
                    System.out.print("Enter your password : ");
                    String loginPassword = sc.next();
                    sc.nextLine(); // Consumes NewLine
                    if (listOfStudents.get(i).password.equals(loginPassword)) {
                        System.out.println("You are successfully logged in as " + listOfStudents.get(i).name + "!");
                        return i;
                    } else {
                        count++;
                        System.out.println(RED + "Invalid Password! Attempts Remaining : " + (3 - count) + RESET);
                    }
                }
            }
        }
        if (count == 3) {
            System.out.println(RED + "No Login Attempts left, Going back to Login Portal!" + RESET);
        }
        if (!found) {
            System.out.println(RED + "No Such Record Found, Going back to Login Portal!" + RESET);
        }
        sc.nextLine(); // Consumes NewLine
        return -1;
    }

    // Method to grant admission to a student based on their ACPC percentile and college cutoffs
    void grantAdmission(Student student) {
        College college = null;
        System.out.print("Enter the name of college : ");
        String collegeName = sc.nextLine();
        // Search for the college by name
        for (College college1 : listOfColleges) {
            if (college1.name.equalsIgnoreCase(collegeName)) {
                college = college1;
            }
        }
        if (college == null) {
            System.out.println(RED + "College not found!" + RESET);
            return;
        }
        // Display available branches/courses for the selected college
        System.out.println("Available Branches : ");
        int i;
        for (i = 0; i < college.course.length; i++) {
            System.out.println("Press " + (i + 1) + " for " + college.course[i]);
        }
        // i now equals the number of available courses

        // Taking and validating the branch choice from the user
        int choice = 0;
        while (choice < 1 || choice > i) {
            System.out.print("Enter your choice : ");
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println(RED + "Invalid Input!" + RESET);
                sc.nextLine(); // Clears bad input from scanner
                continue;
            }
            if (choice < 1 || choice > i) {
                System.out.println(RED + "Enter choice between 1 and " + i + "!" + RESET);
            }
        }

        // Process the admission based on student's percentile and the college's cutoff for the chosen branch
        for (int j = 1; j <= i; j++) {
            if (choice == j) {
                if (student.acpcPercentile >= college.cutOffPercentile[j - 1][student.casteIndex]) {
                    System.out.println("Congratulations! Your Admission in " + college.name + " College in " + college.course[j - 1] + " Branch has been confirmed!");
                    student.feesToBePaid = college.fees;
                    // If the college is a GovernmentCollege, check and apply scholarship
                    if (college instanceof GovernmentCollege gvt) { // gvt is a pattern variable
                        System.out.println("Your egligiblity for scholarship will be checked right now!");
                        gvt.validateScholarship(student);
                    }
                    System.out.println("Fees " + student.feesToBePaid + " has to be paid at Institute level!");
                    // Decrement the available seat count for the chosen branch and caste
                    college.seats[j - 1][student.casteIndex]--;
                    student.admittedInCollege = college;
                    sc.nextLine(); // Consumes NewLine
                    return;
                } else {
                    System.out.println(RED + "Sorry you do not meet the percentile requirements for admission in this college!" + RESET);
                    // If the cutoff is not met for a PrivateCollege, check for management quota option
                    if (college instanceof PrivateCollege pvt) { // pvt is a pattern variable
                        System.out.println("Searching for Management Quota Seats...");
                        if (student.acpcPercentile >= 50) {
                            System.out.println("You can take admission in Management Quota but fees will be doubled!");
                            String yn;
                            do {
                                System.out.print("Would you like to take admission in Management Quota? (Y/N) : ");
                                yn = sc.next();
                                if (!yn.equalsIgnoreCase("Y") && !yn.equalsIgnoreCase("N")) {
                                    System.out.println(RED + "Enter Y or N!" + RESET);
                                }
                            } while (!yn.equalsIgnoreCase("Y") && !yn.equalsIgnoreCase("N"));
                            boolean admissionChoice = yn.equalsIgnoreCase("Y");
                            if (admissionChoice) {
                                System.out.println("Congratulations! Your Admission in " + pvt.name + " College in " + pvt.course[j - 1] + " Branch has been confirmed!");
                                student.feesToBePaid = 2 * (pvt.fees);
                                System.out.println("Fees " + student.feesToBePaid + " has to be paid at Institute level!");
                                student.admittedInCollege = pvt;
                                pvt.managementQuotaSeats--;
                            }
                        } else {
                            System.out.println(RED + "You do not meet the percentile requirements for admission in Management Quota!" + RESET);
                        }
                    }
                }
                break;
            }
        }
        sc.nextLine();
    }

    // Method to display all colleges that a student qualifies for based on their ACPC percentile
    void displayAllCollegeWithinPercentile(Student student) {
        boolean found = false;
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-50s %-20s %-10s \n", "College Name", "Location", "Fees");
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Check each college and its courses to see if the student meets the cutoff for at least one branch
        for (College college : listOfColleges) {
            boolean checked = false;
            for (int j = 0; j < college.course.length; j++) {
                if ((college.cutOffPercentile[j][student.casteIndex] <= student.acpcPercentile) && !checked) {
                    System.out.printf("%-50s %-20s %.2f \n", college.name, college.location, college.fees);
                    found = true;
                    checked = true;
                }
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
        if (!found) {
            System.out.println(RED + "No Colleges within your cutoff!" + RESET);
        }
    }

    // Static block to preload a list of colleges and students into the system
    static {
        listOfColleges.add(new GovernmentCollege("L.D. College of Engineering", "Ahmedabad", 1500, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{98.5, 97.2, 95.0}, {96.0, 94.5, 92.0}, {95.0, 93.0, 90.0}},
                new int[][]{{30, 10, 20}, {25, 8, 15}, {20, 7, 13}}, 50));

        listOfColleges.add(new GovernmentCollege("VGEC", "Chandkheda", 1600, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{97.0, 95.5, 93.0}, {95.0, 93.2, 90.5}, {94.0, 91.5, 88.0}},
                new int[][]{{28, 9, 18}, {24, 7, 12}, {19, 6, 10}}, 45));

        listOfColleges.add(new GovernmentCollege("Dr. S. & S. S. Ghandhy College", "Surat", 1400, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{96.5, 94.8, 92.0}, {94.0, 91.0, 88.5}, {92.5, 89.5, 86.0}},
                new int[][]{{27, 8, 17}, {22, 6, 11}, {18, 5, 9}}, 40));

        listOfColleges.add(new GovernmentCollege("GEC Bhavnagar", "Bhavnagar", 1300, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{95.0, 93.2, 90.5}, {92.0, 89.5, 86.5}, {91.0, 88.0, 84.0}},
                new int[][]{{26, 8, 16}, {21, 6, 10}, {17, 5, 8}}, 38));

        listOfColleges.add(new GovernmentCollege("GEC Rajkot", "Rajkot", 1350, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{95.5, 93.5, 91.0}, {92.5, 90.0, 87.0}, {91.5, 89.0, 85.0}},
                new int[][]{{25, 7, 15}, {20, 5, 9}, {16, 4, 7}}, 37));

        listOfColleges.add(new GovernmentCollege("GEC Modasa", "Modasa", 1250, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{94.0, 92.0, 89.5}, {91.0, 88.5, 85.5}, {90.0, 87.0, 83.0}},
                new int[][]{{24, 7, 14}, {19, 5, 8}, {15, 4, 6}}, 35));

        listOfColleges.add(new GovernmentCollege("Shantilal Shah Engineering College", "Bhavnagar", 1280, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{94.5, 92.5, 90.0}, {91.5, 89.0, 86.0}, {90.5, 87.5, 84.0}},
                new int[][]{{23, 7, 13}, {18, 5, 7}, {14, 4, 5}}, 34));

        listOfColleges.add(new GovernmentCollege("Birla Vishvakarma Mahavidyalaya", "Vallabh Vidyanagar", 1700, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{99.0, 97.8, 95.5}, {97.5, 95.5, 93.0}, {96.5, 94.0, 91.0}},
                new int[][]{{32, 11, 22}, {26, 9, 14}, {21, 8, 12}}, 55));

        listOfColleges.add(new GovernmentCollege("Government Engineering College", "Dahod", 1200, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{93.5, 91.5, 89.0}, {90.5, 88.0, 85.0}, {89.5, 86.5, 82.5}},
                new int[][]{{22, 6, 12}, {17, 4, 6}, {13, 3, 5}}, 30));

        listOfColleges.add(new GovernmentCollege("Lukhdhirji Engineering College", "Morbi", 1400, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{96.0, 94.2, 91.5}, {94.0, 91.5, 88.0}, {93.0, 90.0, 85.5}},
                new int[][]{{28, 9, 18}, {24, 7, 12}, {19, 6, 10}}, 42));

        listOfColleges.add(new PrivateCollege("Nirma University", "Ahmedabad", 250000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{95.0, 92.5, 90.0}, {92.0, 89.5, 86.0}, {91.0, 88.0, 84.5}},
                new int[][]{{60, 20, 30}, {50, 15, 25}, {40, 12, 18}}, 40));

        listOfColleges.add(new PrivateCollege("DAIICT", "Gandhinagar", 300000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{96.5, 94.0, 91.0}, {94.5, 91.5, 88.0}, {93.5, 90.5, 86.5}},
                new int[][]{{55, 18, 28}, {45, 13, 22}, {38, 10, 15}}, 38));

        listOfColleges.add(new PrivateCollege("Ahmedabad University", "Ahmedabad", 200000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{94.0, 91.5, 89.0}, {91.5, 89.0, 85.5}, {90.5, 87.5, 83.5}},
                new int[][]{{50, 17, 27}, {40, 12, 20}, {35, 9, 13}}, 35));

        listOfColleges.add(new PrivateCollege("Pandit Deendayal Energy University", "Gandhinagar", 180000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{93.0, 90.5, 87.5}, {90.0, 87.0, 84.0}, {89.0, 86.0, 81.5}},
                new int[][]{{48, 16, 25}, {38, 11, 18}, {32, 8, 12}}, 32));

        listOfColleges.add(new PrivateCollege("Silver Oak University", "Ahmedabad", 150000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{92.5, 90.0, 86.5}, {90.5, 87.5, 84.0}, {89.5, 86.5, 82.0}},
                new int[][]{{45, 14, 22}, {35, 10, 17}, {30, 7, 10}}, 30));

        listOfColleges.add(new PrivateCollege("Adani Institute of Infrastructure Engineering", "Ahmedabad", 170000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{93.0, 90.5, 88.0}, {90.5, 88.0, 85.0}, {89.5, 86.5, 82.5}},
                new int[][]{{42, 13, 20}, {34, 9, 15}, {28, 6, 9}}, 28));

        listOfColleges.add(new PrivateCollege("Ganpat University", "Mehsana", 160000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{92.0, 89.5, 86.0}, {90.0, 87.0, 83.5}, {89.0, 86.0, 81.0}},
                new int[][]{{40, 12, 18}, {32, 8, 13}, {26, 5, 8}}, 26));

        listOfColleges.add(new PrivateCollege("Parul University", "Vadodara", 130000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{91.0, 88.5, 85.0}, {89.0, 86.0, 82.5}, {88.0, 85.0, 80.0}},
                new int[][]{{36, 10, 16}, {28, 7, 11}, {24, 5, 6}}, 24));

        listOfColleges.add(new PrivateCollege("Indus University", "Ahmedabad", 125000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{90.5, 88.0, 84.5}, {88.5, 85.5, 82.0}, {87.5, 84.5, 79.5}},
                new int[][]{{34, 9, 15}, {27, 6, 10}, {22, 4, 6}}, 22));

        listOfColleges.add(new PrivateCollege("Sal Institute of Technology", "Ahmedabad", 120000, new String[]{"Computer Engineering", "Mechanical Engineering", "Civil Engineering"},
                new double[][]{{90.0, 87.5, 84.0}, {88.0, 85.0, 81.5}, {87.0, 84.0, 79.0}},
                new int[][]{{32, 9, 14}, {26, 6, 9}, {21, 4, 5}}, 21));

        // Adding some default students to the system
        listOfStudents.add(new Student("Suresh", "General", "Suresh0101", 310510, 99, 98, 0, "suresh0101@gmail.com"));
        listOfStudents.add(new Student("Ramesh", "EWS", "Ramesh0101", 333510, 49, 49, 1, "ramesh0101@gmail.com"));
        listOfStudents.add(new Student("Hitesh", "OBC", "Hitesh0101", 810510, 80, 80, 2, "hitesh0101@gmail.com"));
    }
}