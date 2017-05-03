package de.dhbw;

import de.dhbw.Microcontroller.CPU;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("picsim.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();

        Thread emuThread = new Thread(CPU.getInstance());
        emuThread.start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
