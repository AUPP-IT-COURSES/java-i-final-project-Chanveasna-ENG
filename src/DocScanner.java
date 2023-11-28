import javax.swing.*;
import java.awt.*;

import gui.OptionPanel;
import gui.TextPanel;

public class DocScanner {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}

class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("DocScanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(getSize());
        setSize(1500, 1000);
        setLayout(new BorderLayout());

        TextPanel textPanel = new TextPanel();
        add(textPanel);

        OptionPanel optionPanel = new OptionPanel(textPanel);
        add(optionPanel, BorderLayout.WEST);
    }
}
