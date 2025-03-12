/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

/**
 * 
 * @author xpn8tn
 */

public class Player extends Sprite {
    private int velX, velY;
    private GameEngine gameEngine;

    /**
     * Constructs a player with the specified position, image path, and game engine reference.
     * @param x The x-coordinate of the player.
     * @param y The y-coordinate of the player.
     * @param imagePath The file path of the player's image.
     * @param gameEngine The game engine instance.
     */
    public Player(int x, int y, String imagePath, GameEngine gameEngine) {
        super(x, y, 50, 50, imagePath);
        this.velX = 0;
        this.velY = 0;
        this.gameEngine = gameEngine;
    }

    /**
     * Sets the player's velocity.
     * @param dx The change in X direction.
     * @param dy The change in Y direction.
     */
    public void setVelocity(int dx, int dy) {
        this.velX = dx;
        this.velY = dy;
    }

    /**
     * Moves the player based on its velocity, ensuring it does not move out of bounds or pass through obstacles.
     */
    public void move() {
        x += velX;
        y += velY;

        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
        if (y < 0) y = 0;
        if (y > 600 - height) y = 600 - height;

        var obstacles = gameEngine.getObstacles();
        for (var obstacle : obstacles) {
            if (collides(obstacle)) {
                if (velX > 0) {
                    x -= velX;
                } else if (velX < 0) {
                    x -= velX;
                }
                if (velY > 0) {
                    y -= velY;
                } else if (velY < 0) {
                    y -= velY;
                }
            }
        }
    }

    /**
     * Resets the player's position to the initial spawn point.
     */
    public void resetPosition() {
        x = 50;
        y = 50;
    }

    /**
     * Sets the player's position to the specified coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
