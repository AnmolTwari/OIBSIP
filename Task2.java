// OASIS INFOBYTE

//  HII I am anmol tiwari and in this Task 2 i have made a Game named Number Guessig Game

import java.util.Random;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String playAgain;
        int totalScore = 0;

        do {
            int roundScore = playGame(sc);
            totalScore += roundScore;
            System.out.println("Do you want to play again? (yes/no): ");
            playAgain = sc.next();
        } while (playAgain.equalsIgnoreCase("yes"));

        sc.close();
        System.out.println("Thank you for playing! Your final score is: " + totalScore);
    }

    public static int playGame(Scanner sc) {
        Random random = new Random();
        int min = 1;
        int max = 100;
        int randomNumber = random.nextInt((max - min) + 1) + min; //Here we take Random number between 1 and 100

        int guessedNumber = 0;
        int maxAttempts = 5;
        int attempt = 0;
        int score = 0;

        while (guessedNumber != randomNumber && attempt < maxAttempts) { // we used while loop
            System.out.print("Attempt " + (attempt + 1) + " of " + maxAttempts
                    + ": Enter the number you guessed between " + min + " and " + max + ": ");
            guessedNumber = sc.nextInt();

            // Validate the guessed number is within the specified range
            if (guessedNumber < min || guessedNumber > max) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                continue; // Skip the rest of the loop and prompt the user again
            }

            attempt++;

            if (guessedNumber == randomNumber) {
                System.out.println("Congratulations! You guessed the correct number.");
                score = maxAttempts - attempt + 1; // Higher score for fewer attempts
            } else if (guessedNumber < randomNumber) {
                System.out.println("The guessed number is lower than the random number.");
            } else {
                System.out.println("The guessed number is higher than the random number.");
            }
        }

        if (guessedNumber != randomNumber) {
            System.out.println("Sorry, you've used all your attempts. The correct number was " + randomNumber + ".");
        }

        return score;
    }
}
// ThankYou!!