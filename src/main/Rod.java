package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Stack;

public class Rod {

    private Rectangle rect;
    private Stack<Ring> rings;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 250;

    public Rod(int x, int y) {
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.BLACK);
        rings = new Stack<>();
    }

    public void addRing(Ring ring) {
        rings.push(ring);
    }

    public void removeRing() {
        rings.pop();
    }

    public Rectangle getRect() {
        return rect;
    }

    public Stack<Ring> getRings() {
        return rings;
    }

    public Ring topRing() {
        if(!rings.isEmpty()) return rings.peek();
        return null;
    }

}
