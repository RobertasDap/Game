module com.example.javafxgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxgame to javafx.fxml;
    exports com.example.javafxgame;

    opens com.example.javafxgame.controllers to javafx.fxml;
    exports com.example.javafxgame.controllers;
}