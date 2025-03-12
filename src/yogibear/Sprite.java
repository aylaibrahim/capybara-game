/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;
import java.awt.*;
import javax.swing.ImageIcon;

/**
 * 
 * @author xpn8tn
 */

public class Sprite {
    protected int x, y, width, height;
    protected Image image;

    /**
     * Constructs a sprite with the given position, size, and image.
     * 
     * @param x the x-coordinate of the sprite
     * @param y the y-coordinate of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param imagePath the path to the image for the sprite
     */
    public Sprite(int x, int y, int width, int height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = new ImageIcon(imagePath).getImage();
    }

    /**
     * Draws the sprite on the provided Graphics object.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    /**
     * Checks if this sprite collides with another sprite.
     * 
     * @param other the other sprite to check for collision
     * @return true if the sprites collide, false otherwise
     */
    public boolean collides(Sprite other) {
        Rectangle thisRect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        return thisRect.intersects(otherRect);
    }
}
