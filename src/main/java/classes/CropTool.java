package classes;

import gui.ImagePanel;
import gui.OptionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CropTool {
    private static Image image;
    private static ImagePanel imagePanel;
    private static OptionPanel listener;
    private static Point point1;
    private static Point point2;
    private static Point point3;
    private static Point point4;
    public static List<Edge> dots = new ArrayList<>();
    private static boolean transformInProgress = false;
    private static Timer timer;

    public CropTool(ImagePanel imagePanel, OptionPanel listener) {
        CropTool.imagePanel = imagePanel;
        CropTool.listener = listener;
    }

    public static void cropImage() {
        if (listener.getImage() == null) {
            JOptionPane.showMessageDialog(null, "Please open an image first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        image = listener.getImage();
        if (image != null) {
            int[] resizeDimensions = image.getResizeDimensions(840, 840);
            point1 = point1 == null ? new Point(0, 0) : point1;
            point2 = point2 == null ? new Point(resizeDimensions[0], 0) : point2;
            point3 = point3 == null ? new Point(0, resizeDimensions[1]) : point3;
            point4 = point4 == null ? new Point(resizeDimensions[0], resizeDimensions[1]) : point4;
        }
        startStopTransformLoop();
    }

    public static void startStopTransformLoop() {
        if (transformInProgress) {
            transformInProgress = false;
            hideDotsAndUpdate();
        } else {
            transformInProgress = true;
            createDots();
            updateCanvas();
        }
    }

    public static void hideDotsAndUpdate() {
        imagePanel.hideDotsAndUpdate();
    }

    public static void createDots() {
        if (image != null) {
            int[] resizeDimensions = image.getResizeDimensions(840, 840);
            List<Point> points = Arrays.asList(point1, point2, point4, point3);
            for (Point point : points) {
                dots.add(new Edge(imagePanel, point.x, point.y, resizeDimensions[0], resizeDimensions[1]));
            }
            imagePanel.setDots(dots);
            System.out.println("Dots created");
        }
    }

    public static void updateCanvas() {
        if (transformInProgress) {
            if (timer == null) {
                timer = new Timer(100, e -> {
                    connectDots();
                    updateCoordinates();
                    if (!transformInProgress) {
                        ((Timer) e.getSource()).stop();
                        timer = null;
                    }
                });
                timer.start();
            }
        } else if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    public static void connectDots() {
        if (dots.size() != 4) {
            return;
        }
        Graphics g = imagePanel.getGraphics();
        g.setColor(Color.BLUE);

        Point p0 = dots.get(0).getPosition();
        Point p1 = dots.get(1).getPosition();
        Point p2 = dots.get(2).getPosition();
        Point p3 = dots.get(3).getPosition();

        g.drawLine(p0.x, p0.y, p1.x, p1.y);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        g.drawLine(p2.x, p2.y, p3.x, p3.y);
        g.drawLine(p3.x, p3.y, p0.x, p0.y);
    }

    public static void updateCoordinates() {
        if (dots.size() != 4) {
            return;
        }
        point1 = dots.get(0).getPosition();
        point2 = dots.get(1).getPosition();
        point3 = dots.get(3).getPosition();
        point4 = dots.get(2).getPosition();
        listener.updateLabel(point1, point2, point3, point4);
    }
    public static Point getPoint1() {
        return point1;
    }
    public static Point getPoint2() {
        return point2;
    }
    public static Point getPoint3() {
        return point3;
    }
    public static Point getPoint4() {
        return point4;
    }
    public static void resetPoints() {
        point1 = null;
        point2 = null;
        point3 = null;
        point4 = null;
    }
}