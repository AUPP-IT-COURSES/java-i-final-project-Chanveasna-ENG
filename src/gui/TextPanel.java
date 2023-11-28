package gui;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;

public class TextPanel extends JPanel{
    public TextPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());
        Text textField = new Text();
        add(textField);
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
