import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextDemo extends JPanel implements ActionListener {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private static Trie trie;
    public void runRealTime(JTextField textField){
        textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                addTextToArea();

            }

            private void addTextToArea() {
                System.out.println(textField.getText());
                List<Pair<String, String>> lt = trie.suggest(textField.getText());
                textArea.setText("");
                for(Pair<String, String> i : lt){
                    textArea.append(i.getFirst() + ": " + i.getSecond() + "\n");
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

    public TextDemo() {
        super(new GridBagLayout());

        textField = new JTextField(20);
        this.runRealTime(textField);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(text + newline);
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TextDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 400));
        //Add contents to the window.
        frame.add(new TextDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public static void addToTrie(Trie trie){
        try {
            File myObj = new File("in.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int a = Integer.parseInt(myReader.nextLine());
                for(int i = 0; i < a; ++i){
                    String t = myReader.nextLine();
                    trie.insert(data, t);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        TextDemo.trie = new Trie();
        addToTrie(trie);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}