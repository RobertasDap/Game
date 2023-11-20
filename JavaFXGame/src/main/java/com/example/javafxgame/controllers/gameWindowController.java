package com.example.javafxgame.controllers;

import com.example.javafxgame.game;
import com.example.javafxgame.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class gameWindowController
{

    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreCount;

    private game Game;

    @FXML
    public void initialize()
    {
        Game = new game(gamePane, scoreCount);
        Game.start();
    }

    @FXML
    public void onKeyPressed(KeyEvent event)
    {
        if (event.getCode() == KeyCode.Q && !Game.isRunning())
        {
            returnToMainMenu();
        } else {
            Game.handleKeyPress(event.getCode());
        }
    }

    @FXML
    public void onKeyReleased(KeyEvent event)
    {
        if (Game != null)
        {
            Game.handleKeyRelease(event.getCode());
        }
    }

    public void returnToMainMenu()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(launch.class.getResource("mainMenu.fxml"));
            Scene menuScene = new Scene(fxmlLoader.load(), 560, 800);
            Stage stage = (Stage) gamePane.getScene().getWindow();
            stage.setTitle("Battle City");
            stage.setScene(menuScene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println("Failed to load mainMenu.fxml");
            e.printStackTrace();
        }
    }

}