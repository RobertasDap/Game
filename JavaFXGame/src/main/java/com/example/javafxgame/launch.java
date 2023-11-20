package com.example.javafxgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class launch extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(launch.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 640);

        stage.setTitle("Battle City");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}