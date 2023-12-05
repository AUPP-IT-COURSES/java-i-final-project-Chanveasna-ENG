package gui.button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import classes.Image;
import gui.*;

public class UndoCropButton extends JButton {
    public UndoCropButton(Font customFont, ImagePanel imagePanel, OptionPanel listener) {
        setText("Undo Crop");
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
        setFont(customFont);
    addActionListener(e -> {
        if (listener.getImage() == null) {
            JOptionPane.showMessageDialog(null, "Please open an image first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Image image = listener.getImage().applyGaussianBlur(listener.getSmoothness()/10f).convertToBlackAndWhite(listener.getContrast());
        imagePanel.setImage(image.resize(840, 840));
        listener.onBWImageChange(image);
    });
    }
}
