import java.util.InputMismatchException;
import java.util.Scanner;

// Run class containing the main method to execute the application
public class Run {
    // ANSI escape codes for formatting terminal output in color
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";

    public static void main(String args[]) {
        // Create an instance of the CollegeAdmissionSystem
        CollegeAdmissionSystem cas = new CollegeAdmissionSystem();
        // Hardcoded admin password for admin mode
        final String ADMIN_PASSWORD = "admin1234";
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        // Main menu loop for login portal
        do {
            System.out.println("=========================================================================");
            System.out.println("=                        COLLEGE ADMISSION SYSTEM                       =");
            System.out.println("=========================================================================");
            System.out.println("=========================================================================");
            System.out.println("                               LOGIN PORTAL                              ");
            System.out.println("=========================================================================");
            System.out.println("==> Press 1 to Register New Student");
            System.out.println("==> Press 2 to Login Existing Student");
            System.out.println("==> Press 3 to Exit the System");
            choice=-1;
            while (!((choice>=1 && choice<=3) || choice==404)) {
                System.out.print("==> Enter Your Choice : ");
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(RED + "Invalid Input!" + RESET);
                    sc.nextLine(); // Clears bad input from scanner
                    continue;
                }
                if (!((choice>=1 && choice<=3) || choice==404)) {
                    System.out.println(RED + "Invalid Choice!" + RESET);
                }
            }
            sc.nextLine();

            switch (choice) {
                case 1:
                    // New student registration
                    cas.registerStudent();
                    break;

                case 2:
                    // Existing student login
                    int index = cas.loginStudent();    // Method which logs in student and returns the logged in student's index
                    if (index == -1) {
                        break;
                    }
                    Student currentStudent = CollegeAdmissionSystem.listOfStudents.get(index);
                    int studentChoice = -1;
                    // Student menu loop after successful login
                    do {
                        System.out.println("=========================================================================");
                        System.out.println("                              STUDENT MENU                               ");
                        System.out.println("=========================================================================");
                        System.out.println("==> Press 1 to Show All Colleges");
                        System.out.println("==> Press 2 to Search College");
                        System.out.println("==> Press 3 to Show Colleges within your Percentile");
                        System.out.println("==> Press 4 to Take Admission In College");
                        System.out.println("==> Press 5 to Log Out");
                        studentChoice=-1;
                        while (studentChoice<1 || studentChoice>5) {
                            System.out.print("==> Enter Your Choice : ");
                            try {
                                studentChoice = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println(RED + "Invalid Input!" + RESET);
                                sc.nextLine(); // Clears bad input from scanner
                                continue;
                            }
                            if (studentChoice<1 || studentChoice>5) {
                                System.out.println(RED + "Invalid Choice!" + RESET);
                            }
                        }
                        sc.nextLine(); // consumes newline

                        switch (studentChoice) {
                            case 1:
                                cas.displayAllColleges();
                                break;

                            case 2:
                                cas.searchCollege();
                                break;

                            case 3:
                                cas.displayAllCollegeWithinPercentile(currentStudent);
                                break;

                            case 4:
                                // Allow admission only if the student is not already admitted
                                if (currentStudent.admittedInCollege == null) {
                                    cas.grantAdmission(currentStudent);
                                } else {
                                    System.out.println("You have already secured admission in " + currentStudent.admittedInCollege.name);
                                }
                                break;

                            case 5:
                                System.out.println("==> Logging Out...");
                                break;
                        }
                    } while (studentChoice != 5);
                    break;

                case 3:
                    System.out.println("==> Exiting The System.....");
                    break;

                case 404:
                    // Admin mode activated if choice is 404 (a hidden option)
                    System.out.println("=========================================================================");
                    System.out.println("                             ADMIN MODE LOGIN                            ");
                    System.out.println("=========================================================================");
                    System.out.print("==> Enter Admin Password : ");
                    String enteredPassword = sc.next();
                    sc.nextLine();
                    if (ADMIN_PASSWORD.equals(enteredPassword)) {
                        System.out.println("==> Correct Password Entered");
                        System.out.println("==> Welcome to Admin Mode!");
                        int adminChoice = 0;
                        // Admin mode menu loop
                        do {
                            System.out.println("=========================================================================");
                            System.out.println("                             ADMIN MODE MENU                             ");
                            System.out.println("=========================================================================");
                            System.out.println("==> Press 1 to Add College");
                            System.out.println("==> Press 2 to Search College");
                            System.out.println("==> Press 3 to Update College");
                            System.out.println("==> Press 4 to Remove College");
                            System.out.println("==> Press 5 to Show All Colleges");
                            System.out.println("==> Press 6 to Exit Admin Mode");
                            adminChoice=-1;
                            while (adminChoice<1 || adminChoice>6) {
                                System.out.print("==> Enter Your Choice : ");
                                try {
                                    adminChoice = sc.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println(RED + "Invalid Input!" + RESET);
                                    sc.nextLine();
                                    continue;
                                }
                                if (adminChoice<1 || adminChoice>6) {
                                    System.out.println(RED + "Invalid Choice!" + RESET);
                                }
                            }
                            sc.nextLine(); // Consumes NewLine

                            switch (adminChoice) {
                                case 1:
                                    cas.addCollege();
                                    break;

                                case 2:
                                    cas.searchCollege();
                                    break;

                                case 3:
                                    cas.updateCollege();
                                    break;

                                case 4:
                                    cas.removeCollege();
                                    break;

                                case 5:
                                    cas.displayAllColleges();
                                    break;

                                case 6:
                                    System.out.println("Exiting Admin Mode....");
                                    break;
                            }
                        } while (adminChoice != 6);
                    } else {
                        System.out.println(RED + "==> Incorrect Password Entered" + RESET);
                        System.out.println("==> Going Back to Login Portal!");
                    }
                    break;
            }
        } while (choice != 3);
    }
}