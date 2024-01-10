package src.graphics;

import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Graphics;

public class Canvas extends JComponent {
    private int width;
    private int height;

    public Canvas() {
        this(400, 400);
    }

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void resize(int size) {
        this.width = size;
        this.height = size;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void paintComponent(Graphics context) {
        Graphics2D graphics = (Graphics2D) context;
        
        super.paintComponent(graphics);
        this.setPreferredSize(new Dimension(1024, this.getHeight()));
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void setPreferredSize(Dimension preferredSize) {
        this.width = preferredSize.width;
        this.height = preferredSize.height;
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }
}
