import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
public class CatType {
    JFrame mainframe;
    JPanel menupanel,newgamepanel,resultpanel,highscorepanel;
    JButton startbutton;
    char choice;
    public CatType(){
        choice='m';
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        mainframe=new JFrame();
        mainframe.setTitle("CatType");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1024,690);
        mainframe.setIconImage(new ImageIcon("../resources/icon.png").getImage());
        mainframe.getContentPane().setBackground(Color.decode("#808080"));
        mainframe.setLayout(null);
        mainframe.setVisible(true);
        menupanel=new JPanel();
        newgamepanel=new JPanel();
        resultpanel=new JPanel();
        highscorepanel=new JPanel();
    }

    public JButton createbutton(String title){
        JButton button= new JButton();
        button.setText(title);
        button.setHorizontalTextPosition(JLabel.CENTER);
        button.setVerticalTextPosition(JLabel.CENTER);
        button.setForeground(Color.decode("#FF00FF"));
        button.setFont(new Font("Monospaced", Font.BOLD, 30));
        button.setBounds(300,300,150,30);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setLayout(null);
        return button;
    }

    public JPanel createpanel(String color){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(color));
        panel.setSize(1024,960);
        panel.setOpaque(true);
        panel.setBorder(null);
        mainframe.repaint();
        return panel;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        System.out.println("enter y for GUI");
        char select='y';//new Scanner(System.in).next().charAt(0);
        if(select!='y'){
        wpmgame.main(args);
        System.exit(0);
        }
        CatType session=new CatType();
        while(true){
            switch(session.choice){
                case 'm': session.menu();
                break;
                case 'n': session.newgame();
                break;
                case 'r': session.result();
                break;
                case 'h': session.highscore();
                break;
            }
        }
    }

    @SuppressWarnings("resource")
    public void menu() throws InterruptedException{
        menupanel=createpanel("#808080");
        startbutton=createbutton("START");
        mainframe.add(menupanel);
        menupanel.add(startbutton);
        choice='n';
        mainframe.repaint();
        Thread.sleep(5000);
        mainframe.remove(menupanel);
    }

    @SuppressWarnings("resource")
    public  void newgame() throws InterruptedException{
        newgamepanel=createpanel("#0FF000");
        mainframe.add(newgamepanel);
        choice='r';
        Thread.sleep(5000);
        mainframe.remove(newgamepanel);
    }

    @SuppressWarnings("resource")
    public void result() throws InterruptedException{
        resultpanel=createpanel("#0F0F0F");
        mainframe.add(resultpanel);
        choice='h';
        Thread.sleep(5000);
        mainframe.remove(resultpanel);
    }

    @SuppressWarnings("resource")
    public void highscore() throws InterruptedException{
        highscorepanel=createpanel("#000FF0");
        mainframe.add(highscorepanel);
        choice='m';
        Thread.sleep(5000);
        mainframe.remove(highscorepanel);
    }

}