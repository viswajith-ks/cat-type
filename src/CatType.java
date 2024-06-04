import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.html.HTMLEditorKit;

@SuppressWarnings("resource")
public class CatType implements ActionListener, KeyListener, MouseListener {
    JFrame mainframe;
    JPanel menupanel, newgamepanel, resultpanel, highscorepanel;
    JButton startbutton, highscorebutton;
    JLabel catlabel;
    Color pzcolor, themecolor;
    static char choice;
    int cat;
    float time;
    float accuracy, wpm, cps;
    String answerstring, questionstring;
    String[] wquestion;
    String[] wanswer;
    LinkedList<lb> wll;
    nopasteTextField answer;
    public CatType() {
        wll = new LinkedList<lb>();
        pzcolor = Color.decode("#808080");
        themecolor = Color.decode("#6942FF");
        time = 0;
        accuracy = 0;
        wpm = 0;
        cps = 0;
        answerstring = "";
        questionstring = "";
        choice = 'c';
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        mainframe = new JFrame();
        mainframe.setTitle("CatType");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1024, 690);
        mainframe.setPreferredSize(new Dimension(1024, 690));
        mainframe.setLocationRelativeTo(null);
        mainframe.setIconImage(
            new ImageIcon("../resources/icon.png").getImage());
        mainframe.getContentPane().setBackground(pzcolor);
        ;
        mainframe.setLayout(null);
        mainframe.setResizable(false);
        mainframe.setVisible(true);
        menupanel = new JPanel();
        newgamepanel = new JPanel();
        resultpanel = new JPanel();
        highscorepanel = new JPanel();
    }

    public JButton createbutton(String title) {
        JButton button = new JButton();
        button.setText(title);
        button.setHorizontalTextPosition(JLabel.LEFT);
        button.setHorizontalAlignment(JLabel.LEFT);
        button.setVerticalTextPosition(JLabel.CENTER);
        button.setForeground(themecolor);
        button.setFont(new Font("Monospaced", Font.BOLD, 20));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setLayout(null);
        answer = new nopasteTextField();
        return button;
    }

    public JPanel createpanel(String color) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(color));
        panel.setSize(1024, 960);
        panel.setOpaque(true);
        panel.setBorder(null);
        panel.setLayout(null);
        return panel;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("enter y for GUI");
        char select = new Scanner(System.in).next().charAt(0);
        if (select != 'y') {
            wpmgame.main(args);
            System.exit(0);
        }
        CatType session = new CatType();
        session.menu();
    }

    public void menu() {
        clear();
        menupanel = createpanel("#808080");
        startbutton = createbutton("New Game");
        catlabel = new JLabel();
        catlabel.setIcon(new ImageIcon("../resources/catdown.png"));
        catlabel.setBounds(300, 180, 200, 200);
        catlabel.addMouseListener(this);
        menupanel.add(catlabel);
        startbutton.setBounds(500, 250, 180, 30);
        startbutton.setActionCommand("start");
        startbutton.addActionListener(this);
        startbutton.setFont(new Font("Monospaced", Font.BOLD, 20));
        highscorebutton = createbutton("High Scores");
        highscorebutton.setBounds(500, 280, 180, 30);
        highscorebutton.setActionCommand("highscore");
        highscorebutton.addActionListener(this);
        highscorebutton.setFont(new Font("Monospaced", Font.BOLD, 20));
        mainframe.add(menupanel);
        menupanel.add(startbutton);
        menupanel.add(highscorebutton);
        mainframe.repaint();
    }

    public void newgame() {
        clear();
        questionstring = "";
        answerstring = "";
        wquestion = new wpmgame(5).question();
        for (int i = 0; i < wquestion.length; i++)
            questionstring += wquestion[i] + " ";
        newgamepanel = createpanel("#808080");
        JPanel questionpanel = createpanel("#808080");
        questionpanel.setBounds(15, 10, 980, 225);
        JTextArea question = new JTextArea(questionstring);
        question.setTransferHandler(null);
        question.setEditable(false);
        question.setBackground(pzcolor);
        question.getCaret().deinstall(question);
        question.setLineWrap(true);
        question.setFont(new Font("Monospaced", Font.BOLD, 16));
        question.setVisible(true);
        question.setOpaque(true);
        question.setBounds(5, 5, 970, 200);
        questionpanel.add(question);
        answer.setText("");
        answer.setBounds(5, 5, 970, 200);
        answer.setVisible(true);
        answer.setOpaque(true);
        answer.setBackground(Color.decode("#696969"));
        answer.setFont(new Font("Monospaced", Font.BOLD, 16));
        answer.setForeground(Color.decode("#FFFFFF"));
        answer.setCaretColor(Color.BLACK);
        answer.setLineWrap(true);
        JLabel dialog = new JLabel("Start Typing!");
        dialog.setFont(new Font("Monospaced", Font.BOLD, 15));
        dialog.setForeground(themecolor);
        dialog.setBounds(20, 225, 980, 25);
        mainframe.add(dialog);
        JPanel answerpanel = createpanel("#808080");
        answerpanel.setBounds(15, 255, 980, 225);
        answerpanel.add(answer);
        catlabel.setIcon(new ImageIcon("../resources/catup.png"));
        catlabel.setOpaque(false);
        catlabel.setBounds(810, 454, 200, 200);
        catlabel.setLayout(null);
        catlabel.setVisible(true);
        newgamepanel.add(questionpanel);
        newgamepanel.add(answerpanel);
        newgamepanel.add(catlabel);
        mainframe.add(newgamepanel);
        answer.addKeyListener(this);
        mainframe.repaint();
    }

    public void result() {
        clear();
        resultpanel = createpanel("#808080");
        resultpanel.setBounds(0, 0, 1024, 690);
        resultpanel.setBackground(pzcolor);
        JPanel analysis = createpanel("#808080");
        analysis.setBounds(15, 255, 980, 225);
        JEditorPane pane = new JEditorPane();
        pane.setFocusable(false);
        pane.setBackground(Color.decode("#696969"));
        pane.setEditorKit(new HTMLEditorKit());
        pane.setEditable(false);
        pane.setText(calcresult(wquestion, wanswer));
        pane.setBounds(5, 5, 980, 225);
        analysis.add(pane);
        JEditorPane wpmpane = new JEditorPane();
        wpmpane.setFocusable(false);
        wpmpane.setBackground(pzcolor);
        wpmpane.setEditorKit(new HTMLEditorKit());
        wpmpane.setEditable(false);
        wpmpane.setText(
            "<html><body><p><span style=\"font-family: Monospace; font-size: 30; color: #6A5ACD ;\"<span style=\"font-size: 96;\">"
            + String.format("\n%.2f", wpm)
            + "</span> wpm</span></p></body></html>");
        wpmpane.setBounds(15, 10, 475, 225);
        JEditorPane cpspane = new JEditorPane();
        cpspane.setFocusable(false);
        cpspane.setBackground(pzcolor);
        cpspane.setEditorKit(new HTMLEditorKit());
        cpspane.setEditable(false);
        cpspane.setText(
            "<html><body><p><span style=\"font-family: Monospace; font-size: 30; color: #6A5ACD ;\"<span style=\"font-size: 96;\">"
            + String.format("\n%.2f", cps)
            + "</span> cps</span></p></body></html>");
        cpspane.setBounds(500, 10, 475, 225);
        resultpanel.add(wpmpane);
        resultpanel.add(cpspane);
        startbutton.setLocation(300, 600);
        highscorebutton.setLocation(600, 600);
        highscorebutton.addActionListener(this);
        startbutton.addActionListener(this);
        resultpanel.add(startbutton);
        resultpanel.add(highscorebutton);
        analysis.add(pane);
        resultpanel.add(analysis);
        mainframe.add(resultpanel);
        wpm = 0;
        cps = 0;
        accuracy = 0;
        time = 0;
        mainframe.repaint();
    }

    public String calcresult(String[] q, String[] a) {
        clear();
        int k = 0, words = 0, chars = 0;
        String ans =
            "<html><body><b><span style=\" font-family: Monospace; font-size: 15;\">";
        for (int i = 0; i < Math.min(q.length, a.length); i++) {
            for (k = i; a[k] == " "; k++) {
            }
            if (q[i].equals(a[k])) {
                words++;
            }
        }
        chars += words;
        for (int i = 0; i < Math.min(a.length, q.length); i++) {
            for (int j = 0; j < Math.min(a[i].length(), q[i].length()); j++) {
                if (a[i].charAt(j) == q[i].charAt(j)) {
                    chars++;
                    ans += ("<span style=\" color: #69FF42;\">" + a[i].charAt(j)
                        + "</span>");
                } else
                    ans +=
                        ("<span style=\" text-decoration: underline; color: #FF6942;\">"
                            + a[i].charAt(j) + "</span>");
            }
            ans += "  ";
        }
        ans += "</span></b></html></body>";
        accuracy = 100 * ((float) chars / (float) questionstring.length());
        wpm = (float) (60 * words) / (float) time;
        cps = (float) chars / (float) time;
        addtolb(wpm, cps, accuracy, time);
        return ans;
    }

    public void addtolb(float wpm, float cps, float accuracy, float time) {
        lb curr;
        curr = new lb(wpm, cps, accuracy, time);
        if (choice != 'h' && accuracy >= 50.0 && time >= 3.0) {
            float s = (float) (curr.wpm);
            System.out.println("the wpm is " + s);
            for (int j = 0; j <= wll.size(); j++) {
                if (wll.size() == 0) {
                    System.out.println("empty, adding");
                    wll.add(j, curr);
                    break;
                } else if (s <= wll.get(j).wpm) {
                    System.out.println("lesser");
                    wll.add(j, curr);
                    break;
                } else if (j + 1 == wll.size()) {
                    System.out.println("full");
                    wll.add(j + 1, curr);
                    break;
                }
                if (s <= wll.get(j + 1).wpm) {
                    System.out.println("greater");
                    wll.add(j + 1, curr);
                    break;
                }
            }
        }
    }

    public void highscore() {
        clear();
        JTable table = new JTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        JTableHeader header = table.getTableHeader();
        model.addColumn("WPM");
        model.addColumn("CPS");
        model.addColumn("ACCURACY");
        model.addColumn("TIME");
        Object[] row;
        for (int i = wll.size() - 1; i >= 0; i--) {
            row = new Object[4];
            row[0] = wll.get(i).wpm;
            row[1] = wll.get(i).cps;
            row[2] = wll.get(i).accuracy;
            row[3] = wll.get(i).time;
            model.addRow(row);
        }
        header.setBackground(Color.WHITE);
        table.setOpaque(true);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setVisible(true);
        table.setBounds(222, 40, 590, 600);
        header.setBounds(222, 10, 590, 30);
        header.setFocusable(false);
        mainframe.add(header);
        mainframe.add(table);
        mainframe.pack();
        startbutton.addActionListener(this);
        startbutton.setLocation(0, 0);
        mainframe.add(startbutton);
        mainframe.repaint();
    }

    public void clear() {
        mainframe.getContentPane().removeAll();
    }

    public void gotonext() {
        switch (choice) {
            case 'n':
                startbutton.removeActionListener(this);
                highscorebutton.removeActionListener(this);
                answer.removeKeyListener(this);
                clear();
                newgame();
                break;
            case 'h':
                highscorebutton.removeActionListener(this);
                startbutton.removeActionListener(this);
                answer.removeKeyListener(this);
                clear();
                highscore();
                break;
            case 'r':
                answer.removeKeyListener(this);
                highscorebutton.removeActionListener(this);
                startbutton.removeActionListener(this);
                clear();
                result();
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent act) {
        if ("start".equals(act.getActionCommand())) {
            choice = 'n';
            gotonext();
        }
        if ("highscore".equals(act.getActionCommand())) {
            choice = 'h';
            gotonext();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (time == 0.0)
            time = (float) LocalTime.now().toNanoOfDay();
        if (cat == 0) {
            catlabel.setIcon(new ImageIcon("../resources/catleft.png"));
            cat++;
        } else if (cat == 1) {
            catlabel.setIcon(new ImageIcon("../resources/catright.png"));
            cat--;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            if (time != 0) {
                time = LocalTime.now().toNanoOfDay() - time;
                time /= 1000000000.0;
                choice = 'r';
                answerstring = answer.getText();
                wanswer = answerstring.split("\\s+");
                gotonext();
            } else if (time == 0) {
                choice = 'n';
                gotonext();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        catlabel.setIcon(new ImageIcon("../resources/catup.png"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            java.awt.Desktop.getDesktop().browse(
                new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        } catch (IOException | URISyntaxException e1) {
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        catlabel.setIcon(new ImageIcon("../resources/catdown.png"));
        mainframe.pack();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        catlabel.setIcon(new ImageIcon("../resources/catup.png"));
        mainframe.pack();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        catlabel.setIcon(new ImageIcon("../resources/catup.png"));
        mainframe.pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        catlabel.setIcon(new ImageIcon("../resources/catdown.png"));
        mainframe.pack();
    }
}

class nopasteTextField extends JTextArea {
    @Override
    public void paste() {
        System.out.println("bleh cant cheat by pasting :P");
    }
}

class lb {
    float wpm, cps, accuracy, time;
    public lb(float w, float c, float a, float t) {
        wpm = w;
        cps = c;
        accuracy = a;
        time = t;
    }
    public void display() {
        System.out.println(Float.toString(wpm) + Float.toString(cps)
            + Float.toString(accuracy) + Float.toString(time));
    }
}