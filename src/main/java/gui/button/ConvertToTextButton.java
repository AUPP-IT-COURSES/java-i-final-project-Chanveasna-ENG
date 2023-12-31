package gui.button;

import gui.OptionPanel;
import gui.TextPanel;
import net.sourceforge.tess4j.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConvertToTextButton extends JButton {
    public ConvertToTextButton(Font customFont, TextPanel textPanel, OptionPanel listener) {
        setText("Convert to Text");
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
        setFont(customFont);
        addActionListener(e -> {
            if (listener.getBWImage() != null) {
                try {
                    ITesseract instance = new Tesseract();
                    instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
                    String result = instance.doOCR(listener.getBWImage());
                    textPanel.setText(result);
                } catch (TesseractException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while opening the image file. " + ex.getMessage());
                }
            }
        });
    }
}
