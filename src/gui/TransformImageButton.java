package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class TransformImageButton extends JButton {
    public TransformImageButton(Font customFont) {
        setText("Transform Image");
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
        setFont(customFont);
    }
}
