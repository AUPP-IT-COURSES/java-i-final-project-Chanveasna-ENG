//package gui;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//
//class OpenImageButton extends JButton {
//    public OpenImageButton(Font customFont) {
//        setText("Open Image");
//        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
//        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
//        setFont(customFont);
//    }
//}
package gui.button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import classes.CropTool;
import classes.Image;
import gui.ImagePanel;
import interfaces.ImageChangeListener;

import java.io.File;
import javax.imageio.ImageIO;

public class OpenImageButton extends JButton {
    private Image image;
    public OpenImageButton(Font customFont, ImagePanel imagePanel, ImageChangeListener listener) {
        setText("Open Image");
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
        setFont(customFont);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
//                        Clear the old image from memory
                        if (image != null) {
                            image.flush();
                            image = null;
                        }
                        System.gc();

//                        Load the new image
                        BufferedImage bufferedImage = ImageIO.read(selectedFile);
                        if (bufferedImage == null) {
                            throw new Exception("The selected file is not an image. Please select an image file.");
                        }
                        image = new Image(bufferedImage);
                        bufferedImage.flush();
                        imagePanel.setImage(image.convertToBlackAndWhite(128).resize(840, 840));
                        listener.onImageChange(image);
                        listener.onBWImageChange(image.convertToBlackAndWhite(128));
                        CropTool.resetPoints();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error occurred while opening the image file. " + ex.getMessage());
                    }
                }
            }
        });
    }

    public Image getImage() {
        try {
            if (image == null) {
                throw new NullPointerException("No image is loaded.");
            }
            return image;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No image is loaded.");
            return null;
        }
    }
}