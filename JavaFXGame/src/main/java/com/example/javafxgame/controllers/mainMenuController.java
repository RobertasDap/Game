package com.example.javafxgame.controllers;

import com.example.javafxgame.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainMenuController
{
    @FXML
    public Button startButton;
    @FXML
    private VBox menu;

    public void startGame()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(launch.class.getResource("gameWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 640);

            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setTitle("Battle City");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        }
        catch (Exception e)
        {
            System.out.println("Failed to load gameWindow.fxml");
            e.printStackTrace();
        }
    }
    public void exitGame()
    {
        System.exit(0);
    }
}