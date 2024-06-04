/*import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

public class test extends JFrame {
   public test() {
   }

   public static void main(String[] var0) {
      (new test()).open();
   }

   private void open() {
      this.setSize(200, 200);
      JEditorPane pane = new JEditorPane();
      this.setDefaultCloseOperation(3);
      this.setLayout(new BorderLayout());
      pane.setEditorKit(new HTMLEditorKit());
      pane.setEditable(false);
      pane.setText("<html><body>hey <span style=\"color: #ff8000;\">Write in jssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssshere</span></p></body></html>");
      JPanel panel = new JPanel();
      panel.setBackground(Color.RED);
      panel.add(pane);
      JScrollPane scroll = new JScrollPane(panel);
      this.add(scroll, "Center");
      this.setVisible(true);
   }
}
public class test{
public void calcresult(String[] q, String[] a) {
   int k,words,chars;
   float accuracy,wpm,cps,time;
   String ans = "<html><body>";
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
           ans += a[i].charAt(j);
           if (a[i].charAt(j) == q[i].charAt(j)) {
               chars++;
               System.out.print("<span style=\\\"color: #00FF00;\\\">" + a[i].charAt(j) + "</span>");
           } else
               System.out.print("<span style=\\\"color: #00FF00;\\\">" + a[i].charAt(j) + "</span>");
       }
   }
   ans = "<html><body>";
   accuracy = 100 * ((float) chars / (float) question.length());
   wpm = (float)(60 * words) / (float) time;
   cps = (float)chars / (float) time;
   System.out.println("\n\nYour accuracy is " + accuracy + "%");
   System.out.println("Your speed is about " + wpm + " wpm or about " + cps + " cps");
   System.out.println("and you took " + time + " seconds");
   System.out.pritnln(ans);
}
}
*/
class User{
        
    private String firstName;
    private String lastName;
    private int age;
    
    public User(String fn, String ln, int ag){
        this.firstName = fn;
        this.lastName = ln;
        this.age = ag;
    }
    
}



public void populateTableWithLinkedList(){
    
    // create a user linkedList
    LinkedList<User> list = new LinkedList<>();
    
    // create users
    User u1 = new User("AA","BB",10);
    User u2 = new User("BB","CC",20);
    User u3 = new User("CC","DD",30);
    User u4 = new User("DD","EE",40);
    User u5 = new User("EE","FF",50);
    User u6 = new User("LL","MM",80);
    User u7 = new User("NN","TT",100);
    
    // add the users to the list
    list.add(u1);
    list.add(u2);
    list.add(u3);
    list.add(u4);
    list.add(u5);
    list.add(u6);
    list.add(u7);
    
    // get jtable default model
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    
    // populate the jtable with the list
    Object[] row;
    for(int i = 0; i < list.size(); i++){
        row = new Object[3];
        row[0] = list.get(i).firstName;
        row[1] = list.get(i).lastName;
        row[2] = list.get(i).age;
        
        model.addRow(row);
    }
}