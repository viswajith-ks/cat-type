import javax.swing.JTextArea;
class nopasteTextField extends JTextArea {
    @Override
    public void paste() {
        System.out.println("bleh cant cheat by pasting :P");
    }
}
