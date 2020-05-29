package com.luislucas;

import com.fazecast.jSerialComm.SerialPort;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.luislucas.Main.*;

import java.io.OutputStream;
import java.io.PrintStream;


public class ToggleSwitch extends HBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty switchOnProperty() { return switchedOn; }

    private void init() {

        label.setText("Closed");

        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            if (switchedOn.get()) {
                writer.println("close12\\n\\r");
                System.out.println("Close Switch");
                switchedOn.set(!switchedOn.get());
            } else if (!switchedOn.get()) {
                writer.println("open12\\n\\r");
                System.out.println("Open Switch");
                switchedOn.set(!switchedOn.get());
            }
        });
        label.setOnMouseClicked((e) -> {
            if (switchedOn.get()) {
                writer.println("close12\\n\\r");
                System.out.println("Close Switch");
                switchedOn.set(!switchedOn.get());
            } else if (!switchedOn.get()) {
                writer.println("open12\\n\\r");
                System.out.println("Open Switch");
                switchedOn.set(!switchedOn.get());
            }
        });
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        //Default Width
        setWidth(80);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public ToggleSwitch() {
        init();
        switchedOn.addListener((a,b,c) -> {
            if (c) {
                label.setText("Open");
                setStyle("-fx-background-color: green;");
                label.toFront();
            }
            else {
                label.setText("Closed");
                setStyle("-fx-background-color: grey;");
                button.toFront();
            }
        });
    }
}