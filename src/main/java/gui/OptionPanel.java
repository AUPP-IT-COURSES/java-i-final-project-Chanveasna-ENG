package gui;

import classes.CropTool;
import classes.Image;
import classes.ImageUtils;
import gui.button.*;
import interfaces.ImageChangeListener;
import interfaces.ContrastNSmoothnessListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OptionPanel extends JPanel implements ImageChangeListener, ContrastNSmoothnessListener {
    private Image image;
    private Image bwImage;
    private int contrast = 128;
    private int smoothness = 0;
    public JLabel point1Label = new JLabel("Point 1: (0, 0)");
    public JLabel point2Label = new JLabel("Point 2: (0, 0)");
    public JLabel point3Label = new JLabel("Point 3: (0, 0)");
    public JLabel point4Label = new JLabel("Point 4: (0, 0)");
    private ImagePanel imagePanel;
    public OptionPanel(TextPanel textPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImagePanel imagePanel = new ImagePanel();
        add(imagePanel);
        this.imagePanel = imagePanel;
        createListener();


        ButtonPanel buttonPanel = new ButtonPanel(imagePanel, textPanel, this);
        add(buttonPanel);
    }
    public void updateLabel(Point point1, Point point2, Point point3, Point point4) {
        point1Label.setText(point1.x + ", " + point1.y);
        point2Label.setText(point2.x + ", " + point2.y);
        point3Label.setText(point3.x + ", " + point3.y);
        point4Label.setText(point4.x + ", " + point4.y);
    }

    public void createListener() {
        System.out.println("Creating listener");
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof KeyEvent) {
                    KeyEvent keyEvent = (KeyEvent) event;
                    if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
                        handlePaste();
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    public BufferedImage getClipboardImage() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                java.awt.Image image = (java.awt.Image) clipboard.getData(DataFlavor.imageFlavor);
                if (image instanceof BufferedImage) {
                    return (BufferedImage) image;
                } else {
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    bufferedImage.getGraphics().drawImage(image, 0, 0, null);
                    return bufferedImage;
                }
            }
        } catch (UnsupportedFlavorException | IOException e) {
            System.out.println("Error getting clipboard data: " + e);
        }
        return null;
    }

    public void handlePaste() {
        BufferedImage clipboardImage = getClipboardImage();
        if (clipboardImage != null) {
            try {
                Image image = new Image(clipboardImage);
                onImageChange(image);
                onBWImageChange(image.convertToBlackAndWhite(128));
                imagePanel.setImage(image.convertToBlackAndWhite(128).resize(840, 840));
                CropTool.resetPoints();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "The copied image is not valid. Please copy an image and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void onImageChange(Image image) {
        this.image = image;
    }
    public void onBWImageChange(Image image) {
        this.bwImage = image;
    }
    public Image getImage() {
        return image;
    }
    public Image getBWImage() {
        return bwImage;
    }
    public void onContrastChange(int contrast) {
        this.contrast = contrast;
    }
    public void onSmoothnessChange(int smoothness) {
        this.smoothness = smoothness;
    }
    public int getContrast() {
        return contrast;
    }
    public int getSmoothness() {
        return smoothness;
    }
}

class ButtonPanel extends JPanel {
    public ButtonPanel(ImagePanel imagePanel,TextPanel textPanel, OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());

        ImageToolsPanel imageToolsPanel = new ImageToolsPanel(imagePanel, textPanel, optionPanel);
        add(imageToolsPanel, BorderLayout.WEST);

        CropToolsPanel cropToolsPanel = new CropToolsPanel(imagePanel, optionPanel);
        add(cropToolsPanel);
    }
}

class ImageToolsPanel extends JPanel {
    public ImageToolsPanel(ImagePanel imagePanel,TextPanel textPanel, OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());

        ImageToolsButtons buttonsPanel = new ImageToolsButtons(imagePanel,textPanel, optionPanel);
        add(buttonsPanel, BorderLayout.WEST);

        ImageToolsSlider sliderPanel = new ImageToolsSlider(optionPanel);
        add(sliderPanel, BorderLayout.EAST);

    }
}

class ImageToolsButtons extends JPanel {
    Image image;

    public ImageToolsButtons(ImagePanel imagePanel,TextPanel textPanel, OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 20)));

        OpenImageButton openImageButton = new OpenImageButton(customFont, imagePanel, optionPanel);
        add(openImageButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        UpdateImageButton updateImageButton = new UpdateImageButton(customFont, imagePanel, optionPanel);
        add(updateImageButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        ConvertToTextButton convertToTextButton = new ConvertToTextButton(customFont, textPanel, optionPanel);
        add(convertToTextButton);
    }
}

class ImageToolsSlider extends JPanel {
    public ImageToolsSlider(OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel contrastLabel = new JLabel("Contrast: 128");
        contrastLabel.setFont(customFont);
        Slider contrastSlider = new Slider(1, 255, 128, "Contrast", contrastLabel, optionPanel);
        add(contrastSlider);
        add(contrastLabel);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel smoothnessLabel = new JLabel("Smoothness: 0");
        smoothnessLabel.setFont(customFont);
        Slider smoothnessSlider = new Slider(0, 100, 0, "Smoothness", smoothnessLabel, optionPanel);
        add(smoothnessSlider);
        add(smoothnessLabel);
    }
}

class CropToolsPanel extends JPanel {
    public CropToolsPanel(ImagePanel imagePanel, OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());
        CropToolsButtonsPanel cropToolsButtonsPanel = new CropToolsButtonsPanel(imagePanel, optionPanel);
        add(cropToolsButtonsPanel, BorderLayout.WEST);

        CropToolsCoordinatePanel cropToolsCoordinatePanel = new CropToolsCoordinatePanel(optionPanel);
        add(cropToolsCoordinatePanel);
    }
}

class CropToolsButtonsPanel extends JPanel {
    public CropToolsButtonsPanel(ImagePanel imagePanel, OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 20)));

        CropToolButton cropToolButton = new CropToolButton(customFont, imagePanel, optionPanel);
        add(cropToolButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        TransformImageButton transformImageButton = new TransformImageButton(customFont, imagePanel, optionPanel);
        add(transformImageButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        UndoCropButton undoCropButton = new UndoCropButton(customFont, imagePanel, optionPanel);
        add(undoCropButton);
    }
}

class CropToolsCoordinatePanel extends JPanel {
    public CropToolsCoordinatePanel(OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 20, 2, 20)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel point1Label = new JLabel("Point 1: (0, 0)");
        point1Label.setFont(customFont);
        add(optionPanel.point1Label);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel point2Label = new JLabel("Point 2: (0, 0)");
        point2Label.setFont(customFont);
        add(optionPanel.point2Label);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel point3Label = new JLabel("Point 3: (0, 0)");
        point3Label.setFont(customFont);
        add(optionPanel.point3Label);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel point4Label = new JLabel("Point 4: (0, 0)");
        point4Label.setFont(customFont);
        add(optionPanel.point4Label);
    }
}