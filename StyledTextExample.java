/*import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TextPaneTest extends JFrame
{
    private JPanel topPanel;
    private JTextPane tPane;

    public TextPaneTest()
    {
        topPanel = new JPanel();        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            

        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));

        tPane = new JTextPane();                
        tPane.setBorder(eb);
        //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tPane.setMargin(new Insets(5, 5, 5, 5));

        topPanel.add(tPane);

        appendToPane(tPane, "My Name is Too Good.\n", Color.RED);
        appendToPane(tPane, "I wish I could be ONE of THE BEST on ", Color.BLUE);
        appendToPane(tPane, "Stack", Color.DARK_GRAY);
        appendToPane(tPane, "Over", Color.MAGENTA);
        appendToPane(tPane, "flow", Color.ORANGE);

        getContentPane().add(topPanel);

        pack();
        setVisible(true);   
    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}*/

/*
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class test extends JFrame {

    public static void main(String[] args){
        new test().open();
    }

    private void open() {
        setSize(200, 200);
        JEditorPane jp = new JEditorPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //add(jp, BorderLayout.CENTER);
        jp.setEditorKit(new HTMLEditorKit());
        jp.setText("<html><body><p>hey</p><p><span style=\"color: #ff8000;\">Write in jssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssshere</span></p></body></html>");
        JPanel panel=new JPanel();
        panel.setBackground(Color.RED);
        panel.add(jp);
        JScrollPane scrollpanel=new JScrollPane(panel);
        add(scrollpanel,BorderLayout.CENTER);
        JPanel secondpanel=new JPanel();
        secondpanel.setBackground(Color.BLUE);
        add(secondpanel,BorderLayout.SOUTH);
        setVisible(true);
    }

}
*/
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;


public class StyledTextExample {
    public static void main(String[] args)throws BadLocationException {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Styled Text Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextPane textPane = new JTextPane();
            StyledDocument doc = textPane.getStyledDocument();

            Style style = doc.addStyle("default", null);
            StyleConstants.setForeground(style, Color.RED);
            doc.insertString(10, "H", style);


            // Repeat the above for other letters...

            frame.add(textPane);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
