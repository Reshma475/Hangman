import java.util.Random;
import java.util.Scanner;

public class Hangman {

    private static final String[] HANGMAN_PICS = {
        " +---+\n     |\n     |\n     |\n    ===",
        " +---+\n O   |\n     |\n     |\n    ===",
        " +---+\n O   |\n |   |\n     |\n    ===",
        " +---+\n O   |\n/|   |\n     |\n    ===",
        " +---+\n O   |\n/|\\  |\n     |\n    ===",
        " +---+\n O   |\n/|\\  |\n/    |\n    ===",
        " +---+\n O   |\n/|\\  |\n/ \\  |\n    ==="
    };

    private static final String[] WORDS = {
        "spiderman","ironman","thor","punisher","venom","ultron","thanos","ironfist","lukecage","beast","rogue","iceman","nightcrawler","elektra","quicksilver","magneto","gamora","loki","groot","falcon","storm","cyclops","nickfury","deadpool","hulk","wasp","vision","antman", "hawkeye","blackwidow" 
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("H A N G M A N");
        System.out.println("Theme:Marvel Characters");
        String missedLetters = "";
        String correctLetters = "";
        String secretWord = getRandomWord();
        boolean gameIsDone = false;

        while (true) {
            displayBoard(missedLetters, correctLetters, secretWord);

            // Let the player enter a letter.
            String guess = getGuess(missedLetters + correctLetters, scanner);

            if (secretWord.contains(guess)) {
                correctLetters += guess;

                // Check if the player has won.
                boolean foundAllLetters = true;
                for (int i = 0; i < secretWord.length(); i++) {
                    if (!correctLetters.contains(String.valueOf(secretWord.charAt(i)))) {
                        foundAllLetters = false;
                        break;
                    }
                }

                if (foundAllLetters) {
                    System.out.println("Yes! The secret word is \"" + secretWord + "\"! You have won!");
                    gameIsDone = true;
                }
            } else {
                missedLetters += guess;

                // Check if player has guessed too many times and lost.
                if (missedLetters.length() == HANGMAN_PICS.length - 1) {
                    displayBoard(missedLetters, correctLetters, secretWord);
                    System.out.println("You have run out of guesses!\nAfter " + missedLetters.length() + " missed guesses and " + correctLetters.length() + " correct guesses, the word was \"" + secretWord + "\"");
                    gameIsDone = true;
                }
            }

            // Ask the player if they want to play again (but only if the game is done).
            if (gameIsDone) {
                if (playAgain(scanner)) {
                    missedLetters = "";
                    correctLetters = "";
                    gameIsDone = false;
                    secretWord = getRandomWord();
                } else {
                    break;
                }
            }
        }
        scanner.close();
    }

    private static String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(WORDS.length);
        return WORDS[index];
    }

    private static void displayBoard(String missedLetters, String correctLetters, String secretWord) {
        System.out.println(HANGMAN_PICS[missedLetters.length()]);
        System.out.println();
        System.out.print("Missed letters: ");
        for (char letter : missedLetters.toCharArray()) {
            System.out.print(letter + " ");
        }
        System.out.println();

        String blanks = "_".repeat(secretWord.length());
        for (int i = 0; i < secretWord.length(); i++) {
            if (correctLetters.contains(String.valueOf(secretWord.charAt(i)))) {
                blanks = blanks.substring(0, i) + secretWord.charAt(i) + blanks.substring(i + 1);
            }
        }

        for (char letter : blanks.toCharArray()) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    private static String getGuess(String alreadyGuessed, Scanner scanner) {
        while (true) {
            System.out.println("Guess a letter.");
            String guess = scanner.nextLine().toLowerCase();

            if (guess.length() != 1) {
                System.out.println("Please enter a single letter.");
            } else if (alreadyGuessed.contains(guess)) {
                System.out.println("You have already guessed that letter. Choose again.");
            } else if (!"abcdefghijklmnopqrstuvwxyz".contains(guess)) {
                System.out.println("Please enter a LETTER.");
            } else {
                return guess;
            }
        }
    }

    private static boolean playAgain(Scanner scanner) {
        System.out.println("Do you want to play again? (yes or no)");
        String response = scanner.nextLine().toLowerCase();
        return response.startsWith("y");
    }
}
