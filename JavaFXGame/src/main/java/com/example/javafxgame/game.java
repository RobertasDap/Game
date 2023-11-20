package com.example.javafxgame;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Iterator;

public class game
{
    private AnimationTimer timer;
    private boolean isGameRunning;
    private playerTank player;
    private ArrayList<enemyTank> enemies;
    private Pane gamePane;
    private Label scoreLabel;
    private int score;
    private static final int MAX_ENEMIES = 5;
    private long lastSpawnTime = 0;
    private static final long SPAWN_INTERVAL = 3000; // Spawn timer

    private ArrayList<bullet> allBullets;

    public game(Pane gamePane, Label scoreLabel)
    {
        this.gamePane = gamePane;
        this.scoreLabel = scoreLabel;
        this.player = new playerTank(gamePane, 400, 300);
        this.enemies = new ArrayList<>();
        this.score = 0;
        this.allBullets = new ArrayList<>();
        isGameRunning = false;

        for (int i = 0; i < 5; i++)
        {
            spawnEnemy();
        }

        timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                update();
            }
        };
    }

    private void spawnEnemy()
    {
        double padding = 20.0;
        double x = Math.random() * (gamePane.getWidth() - 2 * padding) + padding;
        double y = Math.random() * (gamePane.getHeight() - 2 * padding) + padding;
        enemyTank enemy = new enemyTank(gamePane, x, y);
        enemies.add(enemy);
    }

    public void start()
    {
        timer.start();
        isGameRunning = true;
    }

    private void update()
    {
        player.update();

        for (enemyTank enemy : enemies)
        {
            enemy.update();
            checkCollisionWithPlayer(enemy);
            allBullets.addAll(enemy.getBullets());
            enemy.clearBullets();
        }
        manageEnemySpawning();
        Iterator<bullet> bulletIterator = allBullets.iterator();

        while (bulletIterator.hasNext())
        {
            bullet currentBullet = bulletIterator.next();
            currentBullet.update();
            if (currentBullet.isOutOfBounds())
            {
                gamePane.getChildren().remove(currentBullet.getCircle());
                bulletIterator.remove();
            }
        }

        checkPlayerBulletsCollision();
    }

    private void manageEnemySpawning()
    {
        if (enemies.size() < MAX_ENEMIES && System.currentTimeMillis() - lastSpawnTime > SPAWN_INTERVAL)
        {
            spawnEnemy();
            lastSpawnTime = System.currentTimeMillis();
        }
    }

    private void checkCollisionWithPlayer(enemyTank enemy)
    {
        if (collision.checkCollision(player.getTriangle(), enemy.getTriangle()))
        {
            gameOver();
            return;
        }
        for (bullet bullet : enemy.getBullets())
        {
            if (collision.checkCollision(player.getTriangle(), bullet.getCircle()))
            {
                gameOver();
                return;
            }
        }
    }

    private void checkPlayerBulletsCollision()
    {
        ArrayList<bullet> playerBullets = player.getBullets();
        Iterator<enemyTank> enemyIterator = enemies.iterator();

        while (enemyIterator.hasNext())
        {
            enemyTank enemy = enemyIterator.next();
            Iterator<bullet> bulletIterator = playerBullets.iterator();

            while (bulletIterator.hasNext())
            {
                bullet bullet = bulletIterator.next();
                if (collision.checkCollision(enemy.getTriangle(), bullet.getCircle()))
                {
                    enemyIterator.remove();
                    bulletIterator.remove();
                    gamePane.getChildren().removeAll(enemy.getTriangle(), bullet.getCircle());
                    score += 100;
                    scoreLabel.setText("Score: " + score);
                    break;
                }
            }
        }
    }
    public void handleKeyPress(KeyCode key)
    {
        player.handleKeyPress(key);
    }

    public void handleKeyRelease(KeyCode key)
    {
        if (player != null)
        {
            player.handleKeyRelease(key);
        }
    }

    private void gameOver()
    {
        timer.stop();
        isGameRunning = false;
        displayGameOverMessage();
    }

    private void displayGameOverMessage()
    {
        Text message = new Text("YOU DIED !!!\nIf you want to restart press Q\nYour score: " + score);
        message.setFont(Font.font("Arial", 24));
        message.setTextAlignment(TextAlignment.CENTER);
        message.setX(gamePane.getWidth() / 2 - message.getBoundsInLocal().getWidth() / 2);
        message.setY(gamePane.getHeight() / 2 - message.getBoundsInLocal().getHeight() / 2);

        gamePane.getChildren().add(message);
    }

    public boolean isRunning()
    {
        return isGameRunning;
    }
}