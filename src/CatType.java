import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * The main class for the CatType GUI application.
 * It handles all user interface components and interactions.
 */
public class CatType implements ActionListener, KeyListener, MouseListener, ChangeListener {

    // --- Constants for UI Theme ---
    private static final Color COLOR_BACKGROUND = Color.decode("#808080");
    private static final Color COLOR_THEME = Color.decode("#3A3AC5");
    private static final Color COLOR_BUTTON = Color.decode("#BABADF");
    private static final Color COLOR_TEXT_AREA = Color.decode("#696969");
    private static final Font FONT_MAIN = new Font("Comic Sans MS", Font.PLAIN, 16);
    private static final Border BORDER_DEFAULT = new LineBorder(COLOR_BACKGROUND, 0, false);

    // --- Core Components ---
    private final JFrame mainframe;
    private final WpmGame gameLogic; // Using WpmGame class
    private final noPasteTextArea answerArea;

    // --- UI Elements ---
    private JPanel currentPanel;
    private JButton startButton, highscoreButton;
    private JLabel catLabel;
    private JSpinner wordCountSpinner;
    private JToggleButton capitalizeToggle;

    // --- Game State Variables ---
    private String[] currentQuestionWords;
    private String currentQuestionString;
    private float startTime = 0;

    public CatType() {
        // Initialize the game logic engine with a default difficulty
        this.gameLogic = new WpmGame(20); // Calls the WpmGame constructor with an argument

        mainframe = new JFrame("CatType");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1024, 690);
        mainframe.setLocationRelativeTo(null);
        mainframe.setResizable(false);
        mainframe.getContentPane().setBackground(COLOR_BACKGROUND);
        mainframe.setLayout(null);

        // Load application icon from classpath
        try {
            mainframe.setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
        } catch (Exception e) {
            System.err.println("Could not load icon: /res/icon.png");
        }

        answerArea = new noPasteTextArea();
        answerArea.addKeyListener(this);
    }

    public static void main(String[] args) {
        System.out.println("Enter 't' for Text-based UI (TUI) or any other key for GUI");
        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.next().charAt(0) == 't') {
                WpmGame.main(args); // Calls the WpmGame's main method
            } else {
                System.out.println("Initiating GUI...");
                // Ensure GUI is created on the Event Dispatch Thread (EDT)
                SwingUtilities.invokeLater(() -> {
                    CatType session = new CatType();
                    session.showMenu();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMenu() {
        clearFrame();
        currentPanel = createPanel(COLOR_BACKGROUND, 0, 0, 1024, 690);

        startButton = createButton("New Game", "start", 450, 250, 300, 55);
        startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        startButton.setContentAreaFilled(false);
        startButton.setBorder(null);

        highscoreButton = createButton("High Scores", "highscore", 500, 320, 150, 30);

        catLabel = new JLabel();
        catLabel.setBounds(250, 180, 200, 200);
        catLabel.addMouseListener(this);
        updateCatIcon("/res/catdown.png");

        currentPanel.add(catLabel);
        currentPanel.add(startButton);
        currentPanel.add(highscoreButton);

        mainframe.add(currentPanel);
        mainframe.setVisible(true); // Show frame only after setting up the first panel
        mainframe.repaint();
    }

    private void showNewGame() {
        clearFrame();
        startTime = 0; // Reset timer
        int wordCount = gameLogic.getDifficulty(); // Use getter for difficulty
        int capitalizationFlag = capitalizeToggle != null && capitalizeToggle.isSelected() ? 1 : 0;

        currentQuestionWords = gameLogic.generateQuestion(capitalizationFlag);
        currentQuestionString = String.join(" ", currentQuestionWords);

        currentPanel = createPanel(COLOR_BACKGROUND, 0, 0, 1024, 690);

        // Question display area
        JTextArea questionArea = new JTextArea(currentQuestionString);
        setupReadOnlyTextArea(questionArea);
        JPanel questionPanel = createPanel(COLOR_BACKGROUND, 15, 10, 980, 225);
        questionArea.setBounds(5, 5, 970, 215);
        questionPanel.add(questionArea);

        // Answer input area
        answerArea.setText("");
        answerArea.setFont(FONT_MAIN);
        answerArea.setForeground(Color.WHITE);
        answerArea.setBackground(COLOR_TEXT_AREA);
        answerArea.setCaretColor(Color.WHITE);
        answerArea.setLineWrap(true);
        JPanel answerPanel = createPanel(COLOR_BACKGROUND, 15, 255, 980, 225);
        answerArea.setBounds(5, 5, 970, 215);
        answerPanel.add(answerArea);

        // Controls
        wordCountSpinner = new JSpinner(new SpinnerNumberModel(wordCount, 5, 123, 5));
        wordCountSpinner.setBounds(50, 550, 120, 34);
        wordCountSpinner.setFont(FONT_MAIN);
        wordCountSpinner.addChangeListener(this);
        wordCountSpinner.getEditor().getComponent(0).setBackground(COLOR_BUTTON);

        capitalizeToggle = new JToggleButton("abc");
        capitalizeToggle.setBounds(250, 550, 120, 34);
        capitalizeToggle.setFont(FONT_MAIN);
        capitalizeToggle.setBackground(COLOR_BUTTON);
        capitalizeToggle.setActionCommand("toggleCaps");
        capitalizeToggle.addActionListener(this);
        capitalizeToggle.setSelected(capitalizationFlag == 1);
        capitalizeToggle.setText(capitalizationFlag == 1 ? "Abc" : "abc");

        startButton = createButton("Refresh", "start", 450, 550, 120, 34);

        catLabel = new JLabel();
        catLabel.setBounds(730, 454, 200, 200);
        updateCatIcon("/res/catup.png");

        currentPanel.add(questionPanel);
        currentPanel.add(answerPanel);
        currentPanel.add(wordCountSpinner);
        currentPanel.add(capitalizeToggle);
        currentPanel.add(startButton);
        currentPanel.add(catLabel);

        mainframe.add(currentPanel);
        mainframe.revalidate();
        mainframe.repaint();
        answerArea.requestFocusInWindow();
    }

    private void showResult() {
        clearFrame();
        currentPanel = createPanel(COLOR_BACKGROUND, 0, 0, 1024, 690);

        // WPM and CPS display panes
        JEditorPane wpmPane = createResultPane(String.format("%.2f", gameLogic.getLastWpm()), "wpm", 15, 10);
        JEditorPane cpsPane = createResultPane(String.format("%.2f", gameLogic.getLastCps()), "cps", 500, 10);

        // Detailed analysis pane
        JPanel analysisPanel = createPanel(COLOR_BACKGROUND, 15, 255, 980, 225);
        JEditorPane analysisPane = new JEditorPane();
        analysisPane.setEditable(false);
        analysisPane.setBackground(COLOR_TEXT_AREA);
        analysisPane.setContentType("text/html");
        analysisPane.setText(buildResultHtml(currentQuestionWords, answerArea.getText().trim().split("\\s+")));
        analysisPane.setBounds(5, 5, 970, 215);
        analysisPanel.add(analysisPane);

        // Buttons
        startButton = createButton("New Game", "start", 280, 590, 180, 30);
        highscoreButton = createButton("High Scores", "highscore", 580, 590, 169, 30);

        currentPanel.add(wpmPane);
        currentPanel.add(cpsPane);
        currentPanel.add(analysisPanel);
        currentPanel.add(startButton);
        currentPanel.add(highscoreButton);

        mainframe.add(currentPanel);
        mainframe.revalidate();
        mainframe.repaint();
    }

    private void showHighScores() {
        clearFrame();
        currentPanel = createPanel(COLOR_BACKGROUND, 0, 0, 1024, 690);

        String[] columnNames = { "WPM", "CPS", "Accuracy (%)", "Time (s)" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        LinkedList<LeaderboardEntry> scores = gameLogic.getLeaderboardScores();
        for (LeaderboardEntry score : scores) {
            Object[] row = {
                    String.format("%.2f", score.getWpm()),
                    String.format("%.2f", score.getCps()),
                    String.format("%.2f", score.getAccuracy()),
                    String.format("%.2f", score.getTime())
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        table.setBounds(222, 40, 590, 530);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_MAIN);
        header.setBounds(222, 10, 590, 30);

        startButton = createButton("New Game", "start", 450, 600, 130, 30);

        currentPanel.add(header);
        currentPanel.add(table);
        currentPanel.add(startButton);

        mainframe.add(currentPanel);
        mainframe.revalidate();
        mainframe.repaint();
    }

    private String buildResultHtml(String[] q, String[] a) {
        StringBuilder html = new StringBuilder("<html><body style='font-family: Comic Sans MS; font-size: 15px;'>");
        for (int i = 0; i < Math.min(q.length, a.length); i++) {
            String qWord = q[i];
            String aWord = a[i];
            for (int j = 0; j < aWord.length(); j++) {
                if (j < qWord.length() && aWord.charAt(j) == qWord.charAt(j)) {
                    html.append("<span style='color: #69FF42;'>").append(aWord.charAt(j)).append("</span>");
                } else {
                    html.append("<span style='text-decoration: underline; color: #FF6942;'>").append(aWord.charAt(j))
                            .append("</span>");
                }
            }
            html.append(" ");
        }
        html.append("</body></html>");
        return html.toString();
    }

    // --- Helper Methods for UI Creation ---

    private JButton createButton(String title, String actionCommand, int x, int y, int w, int h) {
        JButton button = new JButton(title);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        button.setBounds(x, y, w, h);
        button.setBackground(COLOR_BUTTON);
        button.setForeground(COLOR_THEME);
        button.setFont(FONT_MAIN);
        button.setFocusable(false);
        button.setBorder(BORDER_DEFAULT);
        return button;
    }

    private JPanel createPanel(Color bgColor, int x, int y, int w, int h) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setBounds(x, y, w, h);
        panel.setLayout(null);
        return panel;
    }

    private void setupReadOnlyTextArea(JTextArea textArea) {
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(COLOR_BACKGROUND);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(FONT_MAIN);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    private JEditorPane createResultPane(String value, String unit, int x, int y) {
        JEditorPane pane = new JEditorPane();
        pane.setEditable(false);
        pane.setBackground(COLOR_BACKGROUND);
        pane.setContentType("text/html");
        pane.setText(String.format(
                "<html><body><p style='font-family: Comic Sans MS; font-size: 30px; color: #6A5ACD;'>" +
                        "<span style='font-size: 96px;'>%s</span> %s</p></body></html>",
                value, unit));
        pane.setBounds(x, y, 475, 225);
        return pane;
    }

    private void updateCatIcon(String path) {
        try {
            catLabel.setIcon(new ImageIcon(getClass().getResource(path)));
        } catch (Exception e) {
            System.err.println("Could not load cat icon: " + path);
        }
    }

    private void clearFrame() {
        mainframe.getContentPane().removeAll();
    }

    // --- Event Listeners ---

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "start":
                showNewGame();
                break;
            case "highscore":
                showHighScores();
                break;
            case "toggleCaps":
                if (capitalizeToggle.isSelected()) {
                    capitalizeToggle.setText("Abc");
                } else {
                    capitalizeToggle.setText("abc");
                }
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // When Enter is pressed, the test ends.
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume(); // Prevent newline from being inserted
            if (startTime != 0) { // Test was in progress
                float endTime = (float) LocalTime.now().toNanoOfDay();
                float timeTaken = (endTime - startTime) / 1_000_000_000.0f;

                String[] answerWords = answerArea.getText().trim().split("\\s+");
                gameLogic.processResult(currentQuestionWords, answerWords, timeTaken);

                showResult();
            } else { // Test hadn't started, treat Enter as a refresh.
                showNewGame();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Start the timer on the very first valid key press.
        if (startTime == 0.0) {
            startTime = (float) LocalTime.now().toNanoOfDay();
        }

        // Animate the cat based on key position
        char keyChar = e.getKeyChar();
        if ("qwertasdfghzxcvb123456".indexOf(keyChar) != -1) {
            updateCatIcon("/res/catleft.png");
        } else if ("yuiopjklmnh7890".indexOf(keyChar) != -1) {
            // Corrected the typo that included "/res/" in the key string
            updateCatIcon("/res/catright.png");
        } else {
            updateCatIcon("/res/catdown.png");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        updateCatIcon("/res/catup.png");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // A little easter egg!
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        } catch (IOException | URISyntaxException ex) {
            // Ignore if it fails
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        updateCatIcon("/res/catdown.png");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        updateCatIcon("/res/catup.png");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateCatIcon("/res/catup.png");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        updateCatIcon("/res/catdown.png");
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == wordCountSpinner) {
            gameLogic.setDifficulty((int) wordCountSpinner.getValue());
        }
    }
}
