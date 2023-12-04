package classes;

import gui.ImagePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Edge {
    private int x, y;
    private final int radius = 6;
    private final Color color = Color.RED;
    private ImagePanel imagePanel;
    private final int screenWidth, screenHeight;

    public Edge(ImagePanel imagePanel, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.screenWidth = width;
        this.screenHeight = height;
        checkCollideWithWall();
        this.imagePanel = imagePanel;
        MouseMotionListener motionListener = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drag(e);
            }
        };

        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (contains(e.getX(), e.getY())) {
                    imagePanel.addMouseMotionListener(motionListener);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                imagePanel.removeMouseMotionListener(motionListener);
            }
        });
    }

    public void drag(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        checkCollideWithWall();
        repaint();
    }

    public void checkCollideWithWall() {
        if (this.x >= this.screenWidth - this.radius) {
            this.x = this.screenWidth - this.radius;
        }
        if (this.y >= this.screenHeight - this.radius) {
            this.y = this.screenHeight - this.radius;
        }
        if (this.x <= this.radius) {
            this.x = this.radius;
        }
        if (this.y <= this.radius) {
            this.y = this.radius;
        }
    }
    public boolean contains(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;
        return dx * dx + dy * dy <= radius * radius;
    }
    public Point getPosition() {
        return new Point(this.x, this.y);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
    }

    public void repaint() {
        imagePanel.repaint();
    }
    public int getRadius() {
        return radius;
    }
}
