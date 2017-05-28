package de.dhbw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.LogManager;

/**
 * Der Einstiegspunkt des Programmes.
 * Es wird die FXML Datei geladen, welche die GUI beschreibt und die Anwendung gestartet
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        LogManager.getLogManager().reset();
        Parent root = FXMLLoader.load(getClass().getResource("picsim.fxml"));
        primaryStage.setTitle("PIC16F84 Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
