package com.example.javafxgame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class bullet
{
    private Pane pane;
    private Circle circle;
    private double x, y;
    private double speed = 10.0;
    private int direction;

    public bullet(Pane pane, double x, double y, int direction)
    {
        this.pane = pane;
        this.x = x;
        this.y = y;
        this.direction = direction;
        createBullet();
    }

    private void createBullet()
    {
        circle = new Circle(x, y, 5, Color.BLACK);
        pane.getChildren().add(circle);
    }

    public void update()
    {
        switch (direction)
        {
            case 0: y -= speed; break; // UP
            case 1: y += speed; break; // DOWN
            case 2: x -= speed; break; // LEFT
            case 3: x += speed; break; // RIGHT
        }

        circle.setCenterX(x);
        circle.setCenterY(y);

        if (isOutOfBounds())
        {
            pane.getChildren().remove(circle);
        }

    }

    public boolean isOutOfBounds()
    {
        return x < 0 || x > pane.getWidth() || y < 0 || y > pane.getHeight();
    }

    public Circle getCircle()
    {
        return circle;
    }
}