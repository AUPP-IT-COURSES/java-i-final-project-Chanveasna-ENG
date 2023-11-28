package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class OptionPanel extends JPanel {
    public OptionPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImagePanel imagePanel = new ImagePanel();
        add(imagePanel);

        ButtonPanel buttonPanel = new ButtonPanel();
        add(buttonPanel);
    }
}

class ImagePanel extends JPanel {
    public ImagePanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setBackground(Color.WHITE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(850, 850);
    }
}

class ButtonPanel extends JPanel {
    public ButtonPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());

        ImageToolsPanel imageToolsPanel = new ImageToolsPanel();
        add(imageToolsPanel, BorderLayout.WEST);

        CropToolsPanel cropToolsPanel = new CropToolsPanel();
        add(cropToolsPanel);
    }
}

class ImageToolsPanel extends JPanel {
    public ImageToolsPanel() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BorderLayout());

        ImageToolsButtons buttonsPanel = new ImageToolsButtons();
        add(buttonsPanel, BorderLayout.WEST);

        ImageToolsSlider sliderPanel = new ImageToolsSlider();
        add(sliderPanel, BorderLayout.EAST);

    }
}

class ImageToolsButtons extends JPanel {
    public ImageToolsButtons() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 20)));

        OpenImageButton openImageButton = new OpenImageButton(customFont);
        add(openImageButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        UpdateImageButton updateImageButton = new UpdateImageButton(customFont);
        add(updateImageButton);

        add(Box.createRigidArea(new Dimension(0, 15)));

        ConvertToTextButton convertToTextButton = new ConvertToTextButton(customFont);
        add(convertToTextButton);
    }
}

class ImageToolsSlider extends JPanel {
    public ImageToolsSlider() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 0), new EmptyBorder(2, 2, 2, 2)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font customFont = new Font("Arial", Font.PLAIN, 16);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel contrastLabel = new JLabel("Contrast: 128");
        contrastLabel.setFont(customFont);
        Slider contrastSlider = new Slider(1, 255, 128, contrastLabel, "Contrast");
        add(contrastSlider);
        add(contrastLabel);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel smoothnessLabel = new JLabel("Smoothness: 0");
        smoothnessLabel.setFont(customFont);
        Slider smoothnessSlider = new Slider(0, 100, 0, smoothnessLabel, "Smoothness");
        add(smoothnessSlider);
        add(smoothnessLabel);
    }
}

class CropToolsPanel extends JPanel {
    public CropToolsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
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