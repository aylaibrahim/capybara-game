/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;
import java.awt.*;

/**
 * 
 * @author xpn8tn
 */

public class Ranger extends Sprite {
    private boolean verticalMovement;
    private int speed = 2;
    private int direction = 1;
    private GameEngine gameEngine;

    /**
     * Constructs a ranger with the specified position, image path, movement direction, dimensions, and game engine reference.
     * @param x The x-coordinate of the ranger.
     * @param y The y-coordinate of the ranger.
     * @param imagePath The file path of the ranger's image.
     * @param verticalMovement True if the ranger moves vertically, false if horizontally.
     * @param width The width of the ranger.
     * @param height The height of the ranger.
     * @param gameEngine The game engine instance.
     */
    public Ranger(int x, int y, String imagePath, boolean verticalMovement, int width, int height, GameEngine gameEngine) {
        super(x, y, width, height, imagePath);
        this.verticalMovement = verticalMovement;
        this.gameEngine = gameEngine;
    }

    /**
     * Moves the ranger, reversing direction upon hitting obstacles or screen edges.
     */
    public void move() {
        if (verticalMovement) {
            y += speed * direction;

            var obstacles = gameEngine.getObstacles();
            for (var obstacle : obstacles) {
                if (collides(obstacle)) {
                    direction *= -1;
                    break;
                }
            }

            if (y <= 0 || y >= 600 - height) {
                direction *= -1;
            }
        } else {
            x += speed * direction;

            var obstacles = gameEngine.getObstacles();
            for (var obstacle : obstacles) {
                if (collides(obstacle)) {
                    direction *= -1;
                    break;
                }
            }

            if (x <= 0 || x >= 800 - width) {
                direction *= -1;
            }
        }
    }

    /**
     * Draws the ranger on the given graphics context and updates its position.
     * @param g The graphics context.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        move();
    }
}
