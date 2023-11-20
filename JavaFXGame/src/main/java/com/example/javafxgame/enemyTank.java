package com.example.javafxgame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Random;

public class enemyTank
{
    private Pane pane;
    private Polygon triangle;
    private double x, y;
    private double speed = 3.0;
    private int direction; // 0: up, 1: down, 2: left, 3: right
    private ArrayList<bullet> bullets;
    private Random random = new Random();
    private long lastDirectionChange = 0;
    private static final long DIRECTION_CHANGE_INTERVAL = 2000; // in milliseconds

    public enemyTank(Pane pane, double x, double y)
    {
        this.pane = pane;
        this.x = x;
        this.y = y;
        this.bullets = new ArrayList<>();
        createTriangle();
        randomizeDirection();
    }

    private void createTriangle()
    {
        triangle = new Polygon();
        triangle.getPoints().addAll(0.0, -15.0, -15.0, 15.0, 15.0, 15.0);
        triangle.setFill(Color.RED);
        triangle.setLayoutX(x);
        triangle.setLayoutY(y);

        pane.getChildren().add(triangle);
    }

    private void randomizeDirection()
    {
        direction = random.nextInt(4);
        updateRotation();
    }

    private void updateRotation()
    {
        switch (direction)
        {
            case 0: triangle.setRotate(0); break;
            case 1: triangle.setRotate(180); break;
            case 2: triangle.setRotate(270); break;
            case 3: triangle.setRotate(90); break;
        }
    }

    public void update()
    {
        if (System.currentTimeMillis() - lastDirectionChange > DIRECTION_CHANGE_INTERVAL)
        {
            randomizeDirection();
            lastDirectionChange = System.currentTimeMillis();
        }

        switch (direction)
        {
            case 0: y -= speed; break;
            case 1: y += speed; break;
            case 2: x -= speed; break;
            case 3: x += speed; break;
        }

        if (x <= 0 || x >= pane.getWidth() - 20)
        {
            x = Math.max(0, Math.min(x, pane.getWidth() - 20));
            randomizeDirection();
        }
        if (y <= 0 || y >= pane.getHeight() - 20)
        {
            y = Math.max(0, Math.min(y, pane.getHeight() - 20));
            randomizeDirection();
        }

        triangle.setLayoutX(x);
        triangle.setLayoutY(y);

        for (bullet bullet : bullets)
        {
            bullet.update();
            if (bullet.isOutOfBounds())
            {
                pane.getChildren().remove(bullet.getCircle());
            }
        }

        bullets.removeIf(bullet::isOutOfBounds);

        if (random.nextDouble() < 0.01)
        {
            shoot();
        }
    }

    public void shoot()
    {
        double offsetX = 20 * Math.cos(Math.toRadians(triangle.getRotate()));
        double offsetY = 20 * Math.sin(Math.toRadians(triangle.getRotate()));
        double bulletStartX = x + triangle.getLayoutBounds().getWidth() / 2 + offsetX;
        double bulletStartY = y + triangle.getLayoutBounds().getHeight() / 2 + offsetY;

        bullet bullet = new bullet(pane, bulletStartX, bulletStartY, direction);
        bullets.add(bullet);
    }

    public Polygon getTriangle()
    {
        return triangle;
    }

    public void clearBullets()
    {
        bullets.clear();
    }

    public ArrayList<bullet> getBullets()
    {
        return bullets;
    }
}