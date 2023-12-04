package classes;

import javax.swing.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.image.BufferedImage;
public class ImageUtils {
    public static BufferedImage getImageFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                Image image = (Image) clipboard.getData(DataFlavor.imageFlavor);
                if (image instanceof BufferedImage) {
                    return (BufferedImage) image;
                } else {
                    // Create a new BufferedImage and draw the image onto it
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    bufferedImage.getGraphics().drawImage(image, 0, 0, null);
                    return bufferedImage;
                }
            }
        } catch (UnsupportedFlavorException | IOException e) {
            JOptionPane.showMessageDialog(null, "Your Clipboard is not image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
