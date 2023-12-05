package gui;

import classes.Image;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import classes.Edge;

public class ImagePanel extends JPanel {
    private Image image;
    private List<Edge> dots = new ArrayList<>();
    public ImagePanel() {
//        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(2, 2, 2, 2)));
        setBackground(Color.WHITE);
    }

    public void setImage(Image image) {
        this.image = image;
//        removeAll();
//        add(new JLabel(new ImageIcon(image)));
//        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(850, 850);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        for (Edge dot : dots) {
            dot.draw(g);
        }
    }

    public void setDots(List<Edge> dots) {
        this.dots = dots;
        repaint();
    }

    public void hideDotsAndUpdate() {
        dots.clear();
        repaint();
    }
}
