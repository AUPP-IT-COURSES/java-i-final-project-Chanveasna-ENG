package gui;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;

public class TextPanel extends JPanel{
    private Text textField;
    public TextPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());
        textField = new Text();
        add(textField);
    }
    public void setText(String text) {
        textField.setText(text);
    }
}

class Text extends JTextArea {
    public Text() {
        super();
        setEditable(true);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
