package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ring {

    private Rectangle rect;
    private boolean dragging;
    private Rod rod;
    private int size;

    public Ring(int size) {
        this.size = size;
        rect = new Rectangle(size, 30);
        rect.setFill(Color.rgb(255 - (int) (size / 1.5), 0, 0));
    }

    public void setXY(int x, int y) {
        rect.setLayoutX(x);
        rect.setLayoutY(y);
    }

    public int getSize() {
        return size;
    }

    public void setRod(Rod r) {
        rod = r;
    }

    public Rod getRod() {
        return rod;
    }

    public void setDragging(boolean b) {
        dragging = b;
    }

    public boolean isDragging() {
        return dragging;
    }

    public Rectangle getRect() {
        return rect;
    }

}