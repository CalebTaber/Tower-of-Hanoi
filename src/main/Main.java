package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private Pane layout;

    private Rod[] rods;
    private Ring[] rings;

    private int width = 800;
    private int height = 300;

    private void draw(int ringNum) {
        // Remove rings in the case that they are being re-drawn
        removeRings();

        // Create the rods and rings
        rods = new Rod[] {
                new Rod(130, height - Rod.HEIGHT),
                new Rod(width / 2 - (Rod.WIDTH / 2), height - Rod.HEIGHT),
                new Rod(650, height - Rod.HEIGHT)
        };


        rings = new Ring[ringNum];
        for(int i = 0; i < rings.length; i++) {
            rings[i] = new Ring(((rings.length + 1) * 30) - (i * 30));
        }

        // Add Rods and rings to the pane
        for(Rod r: rods) {
            layout.getChildren().add(r.getRect());
        }

        for(Ring r: rings) {
            layout.getChildren().add(r.getRect());
            putRing(r, rods[0]); // Add rings to the leftmost rod
        }
    }

    private void setHandlers() {
        for(Ring r: rings) {
            r.getRect().setOnMouseDragged(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if(topOf(r) != -1) {
                        r.setDragging(true);
                        rods[topOf(r)].removeRing();
                        r.setXY((int) event.getSceneX(), (int) event.getSceneY());
                    } else if(r.isDragging()) {
                        r.setXY((int) event.getSceneX(), (int) event.getSceneY());
                    }
                }
            });

            r.getRect().setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if(r.isDragging()) {
                        if(r.getRect().getBoundsInParent().intersects(rods[0].getRect().getBoundsInParent())) putRing(r, rods[0]);
                        else if(r.getRect().getBoundsInParent().intersects(rods[1].getRect().getBoundsInParent())) putRing(r, rods[1]);
                        else if(r.getRect().getBoundsInParent().intersects(rods[2].getRect().getBoundsInParent())) putRing(r, rods[2]);
                        else putRing(r, r.getRod());
                        r.setDragging(false);
                    }
                }
            });
        }
    }

    /**
     * Returns the number of the rod that a ring is on the top of
     * If the ring is not on the top of any rod, the method returns -1
     */
    private int topOf(Ring r) {
        for(int i = 0; i < rods.length; i++) {
            if(!rods[i].getRings().isEmpty()) {
                if(rods[i].topRing() == r) return i;
            }
        }

        return -1;
    }

    /**
     * Puts the given ring on the given rod
     * Changes coordinates
     * Puts ring on top of rod stack
     */
    private void putRing(Ring ring, Rod rod) {
        if(rod.getRings().isEmpty() || rod.topRing().getSize() > ring.getSize()) {
            Rectangle rect = ring.getRect();
            rect.setLayoutX(rod.getRect().getX() - (rect.getWidth() / 2) + (Rod.WIDTH / 2));
            if (rod.getRings().isEmpty()) rect.setLayoutY(height - rect.getHeight());
            else rect.setLayoutY(height - ((rod.getRings().size() + 1) * rect.getHeight()));
            // Add one to the size to compensate for bottom ring

            ring.setRod(rod);
            rod.addRing(ring);
        } else {
            putRing(ring, ring.getRod());
        }
    }

    private void removeRings() {
        if(rings != null) {
            for (Ring r : rings) {
                if (layout.getChildren().contains(r.getRect())) layout.getChildren().remove(r.getRect());
            }
        }

        if(rods != null) {
            for (Rod r : rods) {
                while (!r.getRings().isEmpty()) r.removeRing();
            }
        }
    }

    public void start(Stage window) {
        window = new Stage();
        layout = new Pane();
        Scene main = new Scene(layout);

        window.setWidth(width);
        window.setHeight(height);
        window.setTitle("Tower of Hanoi");
        layout.setBackground(Background.EMPTY);
        main.setFill(Color.DODGERBLUE);
        window.setScene(main);


        draw(4);
        setHandlers();

        MenuBar bar = new MenuBar();
        Menu settings = new Menu("Settings");
        MenuItem rings = new MenuItem("Set # of rings");
        rings.setOnAction(e -> {
            Input.show();
            int num = Integer.parseInt(Input.getInput());
            draw(num);
            setHandlers();
        });

        settings.getItems().add(rings);
        bar.getMenus().add(settings);
        bar.setPrefWidth(width);

        layout.getChildren().add(bar);

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
