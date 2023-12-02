import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocListenerTest extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DocListenerTest r = new DocListenerTest();
            r.setVisible(true);
        });
    }

    public DocListenerTest() {
        super("test");
        getContentPane().setLayout(new GridLayout(5, 2));
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createTitledBorder("TextField"));
        getContentPane().add(textField);
        JTextArea textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createTitledBorder("TextArea"));
        JScrollPane sp = new JScrollPane(textArea);
        getContentPane().add(sp);
        setSize(400, 400);
        textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                addTextToArea();

            }

            private void addTextToArea() {
                textArea.setText("");
                char[] separatedText = textField.getText().toLowerCase().toCharArray();
                boolean separate = true; //Don't know the value of this.
                for (int i = 0; i < separatedText.length; i++) {
                    textArea.append(separate ? ":regional_indicator_" + separatedText[i] + ":\n"
                            : ":regional_indicator_" + separatedText[i] + ":");
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                addTextToArea();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                addTextToArea();
            }
        });
    }
}