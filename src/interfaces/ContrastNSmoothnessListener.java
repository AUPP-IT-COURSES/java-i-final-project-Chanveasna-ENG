package interfaces;

public interface ContrastNSmoothnessListener {
    void onContrastChange(int contrast);
    void onSmoothnessChange(int smoothness);
    int getContrast();
    int getSmoothness();
}
