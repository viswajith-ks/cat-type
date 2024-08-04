import javax.swing.JTextArea;
class noPasteTextField extends JTextArea {
    @Override
    public void paste() {
        System.out.println("bleh cant cheat by pasting :P");
    }
}
