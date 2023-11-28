package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class Slider extends JSlider {

    public Slider(int min, int max, int value, JLabel label, String name) {
        super(JSlider.HORIZONTAL, min, max, value);
        setMajorTickSpacing(1);
        setPaintTicks(true);
        setPreferredSize(new Dimension(300, getPreferredSize().height));
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label.setText(name + ": " + getValue());
            }
        });
    }
}
