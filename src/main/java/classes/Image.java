package classes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Image extends BufferedImage {
    public Image(BufferedImage img) {
        super(img.getWidth(), img.getHeight(), img.getType());
        Graphics g = getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
    }

    public Image resize(int newWidth, int newHeight) {
        int[] dimension = getResizeDimensions(newWidth, newHeight);
        newWidth = dimension[0];
        newHeight = dimension[1];
        java.awt.Image scaledImage = getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, getType());

        Graphics g = resizedImage.getGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();

        return new Image(resizedImage);
    }

    public int[] getResizeDimensions(int newWidth, int newHeight) {
        int width = getWidth();
        int height = getHeight();

        if (height > width) {
            newWidth = width * newHeight / height;
        } else if (width > height) {
            newHeight = height * newWidth / width;
        }

        return new int[]{newWidth, newHeight};
    }

    public Image convertToBlackAndWhite(int buckets) {
        int width = getWidth();
        int height = getHeight();

        BufferedImage bwImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Convert to grayscale
                int gray = (r + g + b) / 3;

                // Convert to black and white
                int bw = gray / buckets == 0 ? 0 : 255;

                bwImage.setRGB(x, y, new Color(bw, bw, bw).getRGB());
            }
        }

        return new Image(bwImage);
    }

    private static Kernel createGaussianKernel(int radius) {
        if (radius < 1)
            radius = 1;
        int size = radius * 2 + 1;
        float[] data = new float[size * size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0;

        for (int y = -radius; y <= radius; y++) {
            int index = 0; // Reset index to 0 at the start of each iteration of the outer loop
            for (int x = -radius; x <= radius; x++) {
                float distance = x * x + y * y;
                data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += data[index];
                index++;
            }
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        return new Kernel(size, size, data);
    }

    private static Kernel createGaussianKernel(float radius) {
        int size = (int) Math.ceil(radius * 2) + 1;
        if (size < 3)
            size = 3;
        float[] data = new float[size * size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0;

        int index = 0;
        outer:
        for (int y = -size / 2; y <= size / 2; y++) {
            for (int x = -size / 2; x <= size / 2; x++) {
                float distance = x * x + y * y;
                data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += data[index];
                index++;
                if (index >= data.length)
                    break outer;
            }
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        return new Kernel(size, size, data);
    }

    public Image applyGaussianBlur(int radius) {
        if (radius == 0)
            return this;
        Kernel kernel = createGaussianKernel(radius);
        BufferedImage blurredImage = new BufferedImage(getWidth(), getHeight(), getType());
        ConvolveOp op = new ConvolveOp(kernel);
        op.filter(this, blurredImage);

        return new Image(blurredImage);
    }

    public Image applyGaussianBlur(float radius) {
        if (radius == 0.0f)
            return this;
        Kernel kernel = createGaussianKernel(radius);
        BufferedImage blurredImage = new BufferedImage(getWidth(), getHeight(), getType());
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        op.filter(this, blurredImage);

        return new Image(blurredImage);
    }
}
//    public Image applyGaussianBlur() {
//        float[] matrix = {
//                1 / 16f, 2 / 16f, 1 / 16f,
//                2 / 16f, 4 / 16f, 2 / 16f,
//                1 / 16f, 2 / 16f, 1 / 16f,
//        };
//
//        BufferedImage blurredImage = new BufferedImage(getWidth(), getHeight(), getType());
//        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, matrix));
//        op.filter(this, blurredImage);
//
//        return new Image(blurredImage);
//    }
