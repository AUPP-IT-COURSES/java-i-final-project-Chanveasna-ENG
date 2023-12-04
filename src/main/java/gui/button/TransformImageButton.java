package gui.button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import classes.CropTool;
import classes.Image;
import gui.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;

public class TransformImageButton extends JButton {
    private Image image;
    public TransformImageButton(Font customFont, ImagePanel imagePanel, OptionPanel listener) {
        setText("Transform Image");
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        setMaximumSize(new Dimension(250, getPreferredSize().height)); // Set maximum width
        setFont(customFont);
        addActionListener(e -> {
            image = listener.getImage();
            if (image == null) {
                JOptionPane.showMessageDialog(null, "Please open an image first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (CropTool.dots.size() != 4) {
                JOptionPane.showMessageDialog(null, "Please crop the image first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            listener.onBWImageChange(imageTransformer(image,
                    getRealCoordinates(CropTool.getPoint1(), image.getWidth(), image.getHeight()),
                    getRealCoordinates(CropTool.getPoint2(), image.getWidth(), image.getHeight()),
                    getRealCoordinates(CropTool.getPoint3(), image.getWidth(), image.getHeight()),
                    getRealCoordinates(CropTool.getPoint4(), image.getWidth(), image.getHeight())).convertToBlackAndWhite(listener.getContrast()).applyGaussianBlur(listener.getSmoothness()/10f));
            imagePanel.setImage(listener.getBWImage());
            CropTool.startStopTransformLoop();
        });
    }
    public org.opencv.core.Point getRealCoordinates(java.awt.Point coordinate, int originalWidth, int originalHeight) {
        int x = coordinate.x;
        int y = coordinate.y;
        int[] resizeDimensions = image.getResizeDimensions(originalWidth, originalHeight);
        int resizedWidth = resizeDimensions[0];
        int resizedHeight = resizeDimensions[1];
        double widthRatio = (double) originalWidth / resizedWidth;
        double heightRatio = (double) originalHeight / resizedHeight;
        return new org.opencv.core.Point((int) Math.round(x * widthRatio), (int) Math.round(y * heightRatio));
    }
    public Image imageTransformer(Image image, Point point1, Point point2, Point point3, Point point4) {
        BufferedImage originalImage = toBufferedImage(image);
        BufferedImage convertedImage;
        if (originalImage.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            convertedImage = originalImage;
        } else {
            convertedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            convertedImage.getGraphics().drawImage(originalImage, 0, 0, null);
        }
        byte[] data = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
        Mat src = new Mat(convertedImage.getHeight(), convertedImage.getWidth(), CvType.CV_8UC3);
        src.put(0, 0, data);

        MatOfPoint2f pts1 = new MatOfPoint2f(point1, point2, point3, point4);
        MatOfPoint2f pts2 = new MatOfPoint2f(
                new org.opencv.core.Point(0, 0),
                new org.opencv.core.Point(image.getHeight(), 0),
                new org.opencv.core.Point(0, image.getWidth()),
                new org.opencv.core.Point(image.getHeight(), image.getWidth())
        );
        Mat transform = Imgproc.getPerspectiveTransform(pts1, pts2);

        Mat dst = new Mat();
        Imgproc.warpPerspective(src, dst, transform, new Size(840, 840));

        // Convert the Mat back to a BufferedImage
// Convert the Mat back to a BufferedImage
        byte[] data1 = new byte[dst.rows() * dst.cols() * (int)(dst.elemSize())];
        dst.get(0, 0, data1);
        BufferedImage outputImage = new BufferedImage(dst.cols(), dst.rows(), dst.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR);
        outputImage.getRaster().setDataElements(0, 0, dst.cols(), dst.rows(), data1);
        return new Image(outputImage);
    }
    public BufferedImage toBufferedImage(Image img) {
        if (img != null) {
            return (BufferedImage) img;
        }

        // Create a new BufferedImage with the same width, height and image type
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the Image onto the BufferedImage
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
