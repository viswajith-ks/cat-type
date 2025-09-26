import javax.swing.JTextArea;

/**
 * A custom JTextArea that disables the paste action to prevent cheating.
 */
class noPasteTextArea extends JTextArea {
    @Override
    public void paste() {
        System.out.println("bleh cant cheat by pasting :P");
        // Overridden to do nothing, effectively disabling paste.
        // You could optionally show a message here.
    }
}
