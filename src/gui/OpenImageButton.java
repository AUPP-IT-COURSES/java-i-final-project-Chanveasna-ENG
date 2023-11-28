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
package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class OpenImageButton extends JButton {
    private BufferedImage image;

    public OpenImageButton(Font customFont) {
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
                        image = ImageIO.read(selectedFile);
                        if (image == null) {
                            throw new Exception("Image is null.");
                        }
                        // Pass the image to other area to process it
                        // processImage(image);
                        JOptionPane.showMessageDialog(null, "Image opened successfully.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error occurred while opening the image file. " + ex.getMessage());
                    }
                }
            }
        });
    }
    public BufferedImage getImage() {
        return image;
    }
}