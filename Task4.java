// Task 4 making a online exam portal using java
import java.io.*;
import java.util.*;

class Candidate {
    String name;
    int roll_number;
    String password;
    int obtained_marks = 0;
    int correct = 0;
    int incorrect = 0;
    public static int test_id = 1111;
    int tst_id = test_id;

    void create_user() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Registration portal! ");
        System.out.println("Enter Your Name");
        name = sc.nextLine();
        System.out.println("Enter your Roll Number ");
        roll_number = sc.nextInt();
        sc.nextLine();  // Consume newline left-over
        System.out.println("Set password ");
        password = sc.nextLine();
        System.out.println("Your registration is successfully completed, Your test id is " + test_id++);
        System.out.println("Press Enter to continue...");
        sc.nextLine(); // Wait for user to press Enter
    }
    

    void update_profile() {// update profile section
        Scanner sc = new Scanner(System.in);
        System.out.println("-------UPDATE PROFILE-------");
        System.out.println("For update \n 1 > Name \n 2 > Roll Number \n 3 > Password");
        int ch = sc.nextInt();
        sc.nextLine();  // Consume newline left-over
        switch (ch) {
            case 1:
                System.out.println("Enter the new name : ");
                name = sc.nextLine();
                break;
            case 2:
                System.out.println("Enter your new Roll Number : ");
                roll_number = sc.nextInt();
                sc.nextLine();  // Consume newline
                break;
            case 3:
                System.out.println("Enter your new Password");
                password = sc.nextLine();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
}

class Portal {
    void login_candidate(Candidate[] usr, int number_students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to you in the test..");
        System.out.println("Enter your test id ");
        int test_id = sc.nextInt();
        System.out.println("Enter your roll number ");
        int roll = sc.nextInt();
        sc.nextLine();  // Consume newline left-over
        System.out.println("Enter your password ");
        String pasw = sc.nextLine();
        for (int i = 0; i < number_students; i++) {
            if (usr[i].tst_id == test_id && usr[i].roll_number == roll && usr[i].password.equals(pasw)) {
                System.out.println("Login Successful");
                System.out.println("Press Enter to begin test..");
                sc.nextLine();
                System.out.println("\033[H\033[2J");
                System.out.flush();
                run_test(usr[i]);
                return;
            }
        }
        System.out.println("Credential Mismatched, try again!");
    }

    void run_test(Candidate user) {
        String fileName = "questions.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Date startTime = new Date();
            Date endTime = new Date(startTime.getTime() + 60 * 1000);
            System.out.println("Test is started at " + startTime + " and will end at " + endTime);
            Scanner sc = new Scanner(System.in);
            while ((line = br.readLine()) != null) {
                if (new Date().before(endTime)) {
                    System.out.println(line); // Display question
                    for (int i = 0; i < 4; i++) { // Display options
                        String option = br.readLine();
                        System.out.println(option);
                    }
                    System.out.println("Enter your answer (A, B, C, or D): ");
                    String input = sc.nextLine().trim().toUpperCase();
                    String ans_fileName = "answer_user_" + user.tst_id + ".txt";
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ans_fileName, true))) {
                        bw.write(input);
                        bw.newLine();
                    } catch (IOException e) {
                        System.err.format("IOException: %s%n", e);
                    }
                } else {
                    System.out.println("Time Out");
                    break;
                }
            }
            System.out.println("Test completed ....\nName: " + user.name + "\nRoll Number: " + user.roll_number + "\nTest ID: " + user.tst_id);
            System.out.println("Press Enter to continue...");
            sc.nextLine();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        evaluate_marks(user);
    }
    

    void evaluate_marks(Candidate user) {
        String userAnswersFile = "answer_user_" + user.tst_id + ".txt";
        String correctAnswersFile = "answers.txt";
    
        try (BufferedReader brUser = new BufferedReader(new FileReader(userAnswersFile));
             BufferedReader brCorrect = new BufferedReader(new FileReader(correctAnswersFile))) {
    
            String userAnswer, correctAnswer;
            int questionNumber = 1;
    
            while ((userAnswer = brUser.readLine()) != null && (correctAnswer = brCorrect.readLine()) != null) {
                // Debug output
                System.out.println("User Answer: " + userAnswer);
                System.out.println("Correct Answer: " + correctAnswer);
    
                // Extract the actual answer from the format Qx. "Answer"
                String[] correctAnswerParts = correctAnswer.split("\"");
                if (correctAnswerParts.length < 2) {
                    System.err.println("Error in format of correct answers file at line: " + correctAnswer);
                    continue;
                }
                correctAnswer = correctAnswerParts[1].trim().toUpperCase();
    
                // User answer is directly read and standardized
                userAnswer = userAnswer.trim().replace("\"", "").toUpperCase();
    
                System.out.println("Question " + questionNumber + ":");
                System.out.println("User Answer: " + userAnswer);
                System.out.println("Correct Answer: " + correctAnswer);
    
                if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                    user.obtained_marks += 1;
                    user.correct++;
                    System.out.println("Result: Correct");
                } else {
                    user.incorrect++;
                    System.out.println("Result: Incorrect");
                }
    
                questionNumber++;
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    
        System.out.println("\n\nThe obtained Marks are: " + user.obtained_marks);
        System.out.println("The number of correct answers is: " + user.correct);
        System.out.println("The number of incorrect answers is: " + user.incorrect);
        System.out.println("Press Enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("Logging Out");
    }
    
}
public class Task4 {// main class
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of candidates you want to register: ");
        int n = sc.nextInt();
        Candidate[] ob = new Candidate[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\033[H\033[2J");
            System.out.flush();
            ob[i] = new Candidate();
            ob[i].create_user();
        }
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("Do you want to update details (Y/N) ");
        char c = sc.next().charAt(0);
        if (c == 'Y' || c == 'y') {
            System.out.println("Enter Test ID for the candidate whose information is to be updated: ");
            int test_id = sc.nextInt();
            for (int i = 0; i < n; i++) {
                if (ob[i].tst_id == test_id) {
                    ob[i].update_profile();
                } else {
                    System.out.println("Credential mismatched");
                }
            }
        }
        System.out.println("\033[H\033[2J");
        System.out.flush();
        Portal ob2 = new Portal();
        while (true) {
            ob2.login_candidate(ob, n);
            System.out.println("\033[H\033[2J");
            System.out.flush();
            System.out.println("Do you want to continue the test portal (Y/N)? ");
            char ch = sc.next().charAt(0);
            if (!(ch == 'Y' || ch == 'y')) {
                break;
            }
        }
        System.out.println("The marks obtained by all the candidates are: ");
        for (int i = 0; i < n; i++) {
            System.out.println("Candidate Roll Number: " + ob[i].roll_number + " Candidate Name: " + ob[i].name + " Obtained Marks: " + ob[i].obtained_marks);
        }
        sc.close();
    }
}
// it was little bit hard for me as a fresher so i did take el from some websites and also from the some videos and i maked it
// ThankYou
