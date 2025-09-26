/**
 * Represents a single entry in the leaderboard.
 * This class encapsulates the results of a single typing test.
 */
public class LeaderboardEntry {
    private final float wpm;
    private final float cps;
    private final float accuracy;
    private final float time;

    public LeaderboardEntry(float wpm, float cps, float accuracy, float time) {
        this.wpm = wpm;
        this.cps = cps;
        this.accuracy = accuracy;
        this.time = time;
    }

    // Getters for encapsulation
    public float getWpm() {
        return wpm;
    }

    public float getCps() {
        return cps;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public float getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("WPM: %.2f, CPS: %.2f, Accuracy: %.2f%%, Time: %.2fs",
                wpm, cps, accuracy, time);
    }
}
