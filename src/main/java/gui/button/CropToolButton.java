package gui.button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import classes.Image;
import classes.CropTool;
import gui.*;


public class CropToolButton extends JButton implements java.awt.event.ActionListener{
    private CropTool cropTool;
    public CropToolButton(Font customFont, ImagePanel imagePanel, OptionPanel listener) {
        setText("Crop Tool");
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
        setFont(customFont);
        new CropTool(imagePanel, listener);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CropTool.cropImage();
    }
}
