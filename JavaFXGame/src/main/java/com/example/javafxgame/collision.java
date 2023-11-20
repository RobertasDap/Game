package com.example.javafxgame;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class collision
{
    // Susidurimas tarp kunu
    public static boolean checkCollision(Polygon triangle1, Polygon triangle2)
    {
        return triangle1.getBoundsInParent().intersects(triangle2.getBoundsInParent());
    }

    // Susidurimas tarp kunu ir sovinio
    public static boolean checkCollision(Polygon triangle, Circle circle)
    {
        return triangle.getBoundsInParent().intersects(circle.getBoundsInParent());
    }

}