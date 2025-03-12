/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

/**
 * 
 * @author xpn8tn
 */

public class Obstacle extends Sprite {
    /**
     * Constructs an obstacle with the specified position, image path, width, and height.
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     * @param imagePath The file path of the obstacle's image.
     * @param width The width of the obstacle.
     * @param height The height of the obstacle.
     */
    public Obstacle(int x, int y, String imagePath, int width, int height) {
        super(x, y, width, height, imagePath);
    }
}
