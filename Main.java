package com.luislucas;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.fazecast.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main extends Application {

    Button open;
    Button close;
    Button status;
    public SerialPort comPort = SerialPort.getCommPorts()[0];
    public StringBuilder sb = new StringBuilder();
    public OutputStream outputStream = comPort.getOutputStream();
    public PrintStream writer = new PrintStream(outputStream);

    void openLatch() {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SerialPort comPort = SerialPort.getCommPorts()[0];
        StringBuilder sb = new StringBuilder();
        comPort.openPort();
        comPort.setBaudRate(38400);
        comPort.setParity(SerialPort.NO_PARITY);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setNumDataBits(8);
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                sb.setLength(0);
                byte[] newData = event.getReceivedData();
                for (int i = 0; i < newData.length; ++i) {
                    sb.append((char)newData[i]);
                }
                String str = sb.toString();
                System.out.println(str.replace("\n", "").replace("\r", ""));
            }
        });
        OutputStream outputStream = comPort.getOutputStream();
        PrintStream writer = new PrintStream(outputStream);
        System.out.println(comPort.getSystemPortName() + " " + comPort.getPortDescription() + " " + comPort.getDescriptivePortName());
        primaryStage.setTitle("Raindrop");

        ToggleSwitch button = new ToggleSwitch();

        open = new Button();
        open.setText("Open");
        open.setOnAction(e -> {
            System.out.println("Open");
            writer.println("open12\\n\\r");
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        close = new Button();
        close.setText("Close");
        close.setOnAction(e -> {
            System.out.println("Close");
            writer.println("close12\\n\\r");
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        status = new Button();
        status.setText("Print Status");
        status.setOnAction(e -> {
            System.out.println("Status");
            writer.println("status12\\n\\r");
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(20);
        layout.getChildren().add(open);
        layout.getChildren().add(close);
        layout.getChildren().add(status);
        layout.getChildren().add(button);
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
