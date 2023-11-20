package com.example.javafxgame;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class playerTank
{
        private Pane pane;
        private Polygon triangle;
        private double x, y;
        private double speed = 5.0;
        private KeyCode lastPressedKey;
        private ArrayList<bullet> bullets;

        public playerTank(Pane pane, double x, double y)
        {
            this.pane = pane;
            this.x = x;
            this.y = y;
            this.bullets = new ArrayList<>();
            createTriangle();
        }

    private void createTriangle()
    {
        triangle = new Polygon();
        // Adjusting points for a fatter triangle
        triangle.getPoints().addAll(0.0, -15.0, -15.0, 15.0, 15.0, 15.0);
        triangle.setFill(Color.BLUE);
        triangle.setLayoutX(x);
        triangle.setLayoutY(y);

        pane.getChildren().add(triangle);
    }

        public void update()
        {
            if (lastPressedKey == KeyCode.W)
            {
                y -= speed;
                triangle.setRotate(0);
            }
            else if (lastPressedKey == KeyCode.S)
            {
                y += speed;
                triangle.setRotate(180);
            }
            else if (lastPressedKey == KeyCode.A)
            {
                x -= speed;
                triangle.setRotate(270);
            }
            else if (lastPressedKey == KeyCode.D)
            {
                x += speed;
                triangle.setRotate(90);
            }

            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x > pane.getWidth() - 20) x = pane.getWidth() - 20;
            if (y > pane.getHeight() - 20) y = pane.getHeight() - 20;

            triangle.setLayoutX(x);
            triangle.setLayoutY(y);

            ArrayList<bullet> bulletsToRemove = new ArrayList<>();

            for (bullet bullet : bullets)
            {
                bullet.update();
                if (bullet.isOutOfBounds())
                {
                    bulletsToRemove.add(bullet);
                }
            }
            bullets.removeAll(bulletsToRemove);
        }

    public void shoot()
    {
        double rotation = triangle.getRotate();
        double bulletStartX = x + triangle.getLayoutBounds().getWidth() / 2;
        double bulletStartY = y + triangle.getLayoutBounds().getHeight() / 2;
        int bulletDirection = 0;

        // Reikalauja tweaks kad issautu per viduri
        double tipOffsetX = 15 * Math.cos(Math.toRadians(rotation));
        double tipOffsetY = 15 * Math.sin(Math.toRadians(rotation));

        if (rotation == 0) // W
        {
            bulletDirection = 0; // UP
            bulletStartX += tipOffsetX;
            bulletStartY += tipOffsetY;
        }
        else if (rotation == 90) // D
        {
            bulletDirection = 3; // RIGHT
            bulletStartX += tipOffsetX;
            bulletStartY += tipOffsetY;
        }
        else if (rotation == 180) // S
        {
            bulletDirection = 1; // DOWN
            bulletStartX -= tipOffsetX;
            bulletStartY -= tipOffsetY;
        }
        else if (rotation == 270) // A
        {
            bulletDirection = 2; // Left
            bulletStartX -= tipOffsetX;
            bulletStartY -= tipOffsetY;
        }

        bullet bullet = new bullet(pane, bulletStartX, bulletStartY, bulletDirection);
        bullets.add(bullet);
    }


    public void handleKeyPress(KeyCode key)
    {
        if (key == KeyCode.K)
        {
            shoot();
        }
        else
        {
            lastPressedKey = key;
        }
    }

        public void setLastPressedKey(KeyCode key)
        {
            this.lastPressedKey = key;
        }

    public void handleKeyRelease(KeyCode key)
    {
        if (this.lastPressedKey == key)
        {
            this.lastPressedKey = null;
        }
    }

        public Polygon getTriangle()
        {
            return triangle;
        }

        public ArrayList<bullet> getBullets()
        {
            return bullets;
        }
}
