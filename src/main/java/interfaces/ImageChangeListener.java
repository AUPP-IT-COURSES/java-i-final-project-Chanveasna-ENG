package interfaces;

import classes.Image;

public interface ImageChangeListener {
    void onImageChange(Image image);
    void onBWImageChange(Image image);
    Image getImage();
    Image getBWImage();
}
