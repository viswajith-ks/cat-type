import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * The core engine for the typing speed game.
 * Manages word generation, result calculation, leaderboard, and the Text-based
 * UI (TUI).
 */
public class WpmGame { // Class name is WpmGame
    private LinkedList<LeaderboardEntry> leaderboardScores;
    private String[] collections;
    private int difficulty;

    // Last game results
    private float lastWpm = 0;
    private float lastCps = 0;
    private float lastAccuracy = 0;
    private float lastTime = 0;

    // Corrected constructor name and logic
    public WpmGame(int initialDifficulty) { // Constructor name matches class name, no return type
        this.leaderboardScores = new LinkedList<>();
        this.difficulty = initialDifficulty;
        this.collections = loadWordsFromFile("/res/words.txt");
    }

    // Constructor for TUI main method, providing a default initial difficulty
    public WpmGame() {
        this(20); // Calls the other constructor with a default difficulty
    }

    private String[] loadWordsFromFile(String path) {
        List<String> words = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Error: Word file not found at " + path);
                // Small default word list as a fallback
                return new String[] { "hello", "world", "typing", "game", "java" };
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading word file.");
            e.printStackTrace();
            // Fallback in case of an error
            return new String[] { "error", "reading", "file" };
        }

        if (words.isEmpty()) {
            System.err.println("Warning: Word file is empty.");
            return new String[] { "empty", "word", "list" };
        }

        // Convert the List<String> to a String[] array
        return words.toArray(new String[0]);
    }

    // --- Public API for GUI Interaction ---

    public String[] generateQuestion(int flag) { // 0 for lowercase, 1 for capitalized
        String[] wquestion = new String[difficulty];
        Random rand = new Random();
        for (int i = 0; i < difficulty; i++) {
            String word = collections[rand.nextInt(collections.length)]; // Use .length
            if (flag == 1) {
                wquestion[i] = Character.toTitleCase(word.charAt(0)) + word.substring(1);
            } else {
                wquestion[i] = word;
            }
        }
        return wquestion;
    }

    public void processResult(String[] questionWords, String[] answerWords, float timeTaken) {
        if (questionWords == null || answerWords == null || timeTaken <= 0) {
            return;
        }

        int correctWords = 0;
        int correctChars = 0;
        int totalQuestionChars = 0;

        for (String s : questionWords) {
            totalQuestionChars += s.length();
        }
        totalQuestionChars += questionWords.length - 1; // Account for spaces between words

        // Calculate correct words
        for (int i = 0; i < Math.min(questionWords.length, answerWords.length); i++) {
            if (questionWords[i].equals(answerWords[i])) {
                correctWords++;
            }
        }

        // Calculate correct characters for accuracy
        for (int i = 0; i < Math.min(questionWords.length, answerWords.length); i++) {
            String qWord = questionWords[i];
            String aWord = answerWords[i];
            for (int j = 0; j < Math.min(qWord.length(), aWord.length()); j++) {
                if (qWord.charAt(j) == aWord.charAt(j)) {
                    correctChars++;
                }
            }
        }

        // Update last game results
        this.lastTime = timeTaken;
        this.lastAccuracy = totalQuestionChars > 0 ? 100.0f * correctChars / totalQuestionChars : 0.0f;
        this.lastWpm = (60.0f * correctWords) / timeTaken;
        this.lastCps = correctChars / timeTaken;

        // Add to leaderboard if it's a valid attempt
        if (this.lastAccuracy >= 50.0 && this.lastTime >= 3.0) {
            addToLeaderboard(new LeaderboardEntry(lastWpm, lastCps, lastAccuracy, lastTime));
        }
    }

    private void addToLeaderboard(LeaderboardEntry entry) {
        leaderboardScores.add(entry);
        // Sorts the list in descending order of WPM (highest first)
        Collections.sort(leaderboardScores, new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry o1, LeaderboardEntry o2) {
                return Float.compare(o2.getWpm(), o1.getWpm());
            }
        });
    }

    // --- Getters for the GUI ---
    public LinkedList<LeaderboardEntry> getLeaderboardScores() {
        return leaderboardScores;
    }

    public float getLastWpm() {
        return lastWpm;
    }

    public float getLastCps() {
        return lastCps;
    }

    public float getLastAccuracy() {
        return lastAccuracy;
    }

    public float getLastTime() {
        return lastTime;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int diff) {
        this.difficulty = diff;
    }

    // --- TUI (Text-based User Interface) Section ---

    public static void main(String[] args) throws InterruptedException {
        WpmGame session = new WpmGame(); // Calls the no-arg constructor
        session.runTUI();
    }

    public void runTUI() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        char choice = 'm';
        while (choice != 'q') {
            switch (choice) {
                case 'n':
                    newGameTUI(sc);
                    choice = 'm'; // Return to menu after game
                    break;
                case 'h':
                    displayHighScoresTUI();
                    System.out.println("\nPress 'm' for menu or 'q' to quit.");
                    choice = sc.next().charAt(0);
                    break;
                case 'm':
                    clrscr();
                    System.out.println(
                            "\n\tNEW GAME (\033[44mn\033[0m)\n\tHIGH SCORES (\033[44mh\033[0m)\n\tQUIT (\033[44mq\033[0m)");
                    choice = sc.next().charAt(0);
                    break;
                default:
                    System.out.println("OOPS! INVALID CHOICE. Returning to menu.");
                    TimeUnit.SECONDS.sleep(1);
                    choice = 'm';
                    break;
            }
        }
        clrscr();
        System.out.println("THANK YOU FOR PLAYING!");
        TimeUnit.SECONDS.sleep(1);
        clrscr();
        sc.close();
    }

    private void newGameTUI(Scanner sc) throws InterruptedException {
        clrscr();
        System.out.println("Enter the number of words:");
        setDifficulty(sc.nextInt());
        System.out.println("Enter 1 for Capitalized Test, 0 for lowercase:");
        int flag = (sc.nextInt() == 1) ? 1 : 0;

        clrscr();
        String[] questionWords = generateQuestion(flag);
        String questionString = String.join(" ", questionWords);

        countdown();
        System.out.println(questionString + "\n");

        sc.nextLine(); // Consume newline
        double start = LocalTime.now().toNanoOfDay();
        String answerString = sc.nextLine();
        double end = LocalTime.now().toNanoOfDay();
        float time = (float) ((end - start) / 1_000_000_000.0);

        String[] answerWords = answerString.trim().split("\\s+");

        // Process the result using the core logic
        processResult(questionWords, answerWords, time);

        printResultTUI(questionWords, answerWords, questionString);

        System.out.println("\nPress any key to continue...");
        sc.next();
    }

    private void printResultTUI(String[] q, String[] a, String questionString) throws InterruptedException {
        clrscr();
        System.out.println(questionString + "\n");
        for (int i = 0; i < Math.min(a.length, q.length); i++) {
            String qWord = q[i];
            String aWord = a[i];
            for (int j = 0; j < Math.min(aWord.length(), qWord.length()); j++) {
                TimeUnit.MILLISECONDS.sleep(3);
                if (aWord.charAt(j) == qWord.charAt(j)) {
                    System.out.print("\033[31;32;1m" + aWord.charAt(j) + "\033[0m");
                } else {
                    System.out.print("\033[31;1;4m" + aWord.charAt(j) + "\033[0m");
                }
            }
            System.out.print(" ");
        }
        System.out.printf("\n\nAccuracy : %.2f%%", getLastAccuracy());
        System.out.printf("\nSpeed : %.2f wpm or about %.2f cps", getLastWpm(), getLastCps());
        System.out.printf("\nTime : %.2f seconds", getLastTime());
    }

    private void displayHighScoresTUI() {
        clrscr();
        System.out.println("--- HIGH SCORES ---");
        System.out.println("(WPM, CPS, Accuracy, Time)");
        if (leaderboardScores.isEmpty()) {
            System.out.println("No scores yet. Play a game!");
            return;
        }

        for (int i = 0; i < leaderboardScores.size(); i++) {
            LeaderboardEntry score = leaderboardScores.get(i);
            String prefix = "";
            String suffix = "";
            if (i == 0) {
                prefix = "\033[33m";
                suffix = "\033[0m 🥇";
            } // Gold
            else if (i == 1) {
                prefix = "\033[36m";
                suffix = "\033[0m 🥈";
            } // Cyan
            else if (i == 2) {
                prefix = "\033[35m";
                suffix = "\033[0m 🥉";
            } // Magenta

            System.out.printf("%s%.2f wpm   %.2f cps   %.2f%%   %.2fs%s\n",
                    prefix, score.getWpm(), score.getCps(), score.getAccuracy(), score.getTime(), suffix);
        }
    }

    private void countdown() throws InterruptedException {
        for (int count = 3; count > 0; count--) {
            System.out.println("NEW GAME IN...");
            System.out.println("  " + count);
            TimeUnit.SECONDS.sleep(1);
            clrscr();
        }
        System.out.println("GO!\n");
    }

    private static void clrscr() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            // Silently fail if clear screen is not possible
        }
    }
}
