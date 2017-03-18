package main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Input {

    private static String s;

    public static void show() {
        Stage window = new Stage();
        Pane layout = new Pane();
        Scene main = new Scene(layout);
        window.setWidth(240);
        window.setHeight(100);

        Label l = new Label("Input a number");
        l.setLayoutX(10);
        l.setLayoutY(10);

        TextField f = new TextField();
        f.setLayoutX(10);
        f.setLayoutY(window.getHeight() / 2 - 10);

        Button b = new Button("Ok");
        b.setLayoutX(180);
        b.setLayoutY(window.getHeight() / 2 - 10);
        b.setOnAction(e -> {
            s = f.getText();
            window.close();
        });

        layout.getChildren().addAll(l, f, b);

        window.setTitle("Input a number");
        window.setScene(main);
        window.showAndWait();
    }

    public static String getInput() {
        return s;
    }

}
