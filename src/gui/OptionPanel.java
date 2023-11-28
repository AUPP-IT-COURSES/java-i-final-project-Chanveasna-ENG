package gui;

import image.Image;
import interfaces.ImageChangeListener;
import interfaces.ContrastNSmoothnessListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class OptionPanel extends JPanel implements ImageChangeListener, ContrastNSmoothnessListener {
    private Image image;
    private Image bwImage;
    private int contrast = 128;
    private int smoothness = 0;

    public OptionPanel(TextPanel textPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImagePanel imagePanel = new ImagePanel();
        add(imagePanel);

        ButtonPanel buttonPanel = new ButtonPanel(imagePanel, textPanel, this);
        add(buttonPanel);
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

class ImagePanel extends JPanel {
    public ImagePanel() {
//        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setBackground(Color.WHITE);
    }
    public void setImage(Image image) {
        removeAll();
        add(new JLabel(new ImageIcon(image)));
        revalidate();
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(850, 850);
    }
}

class ButtonPanel extends JPanel {
    public ButtonPanel(ImagePanel imagePanel,TextPanel textPanel, OptionPanel optionPanel) {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());

        ImageToolsPanel imageToolsPanel = new ImageToolsPanel(imagePanel,textPanel, optionPanel);
        add(imageToolsPanel, BorderLayout.WEST);

        CropToolsPanel cropToolsPanel = new CropToolsPanel();
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
    public CropToolsPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());
        CropToolsButtonsPanel cropToolsButtonsPanel = new CropToolsButtonsPanel();
        add(cropToolsButtonsPanel, BorderLayout.WEST);

        CropToolsCoordinatePanel cropToolsCoordinatePanel = new CropToolsCoordinatePanel();
        add(cropToolsCoordinatePanel);
    }
}

class CropToolsButtonsPanel extends JPanel {
    public CropToolsButtonsPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 20)));

        CropToolButton cropToolButton = new CropToolButton(customFont);
        add(cropToolButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        TransformImageButton transformImageButton = new TransformImageButton(customFont);
        add(transformImageButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        UndoCropButton undoCropButton = new UndoCropButton(customFont);
        add(undoCropButton);
    }
}

class CropToolsCoordinatePanel extends JPanel {
    public CropToolsCoordinatePanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 20, 2, 20)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel point1Label = new JLabel("Point 1: (0, 0)");
        point1Label.setFont(customFont);
        add(point1Label);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel point2Label = new JLabel("Point 2: (0, 0)");
        point2Label.setFont(customFont);
        add(point2Label);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel point3Label = new JLabel("Point 3: (0, 0)");
        point3Label.setFont(customFont);
        add(point3Label);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel point4Label = new JLabel("Point 4: (0, 0)");
        point4Label.setFont(customFont);
        add(point4Label);
    }
}