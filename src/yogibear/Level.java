/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author xpn8tn
 */

public class Level {
    private final int GRID_WIDTH = 800;
    private final int GRID_HEIGHT = 600;
    private final int CELL_SIZE = 50;
    private final int MIN_CLEARANCE = 100;
    private final int PLAYER_CLEARANCE = 80;
    private final int MAX_OBSTACLES = 20;
    private final int MAX_BASKETS = 15;
    private final int MAX_RANGERS = 10;

    private GameEngine gameEngine;

    /**
     * Constructs a Level with a given GameEngine.
     * 
     * @param gameEngine the GameEngine instance to associate with the level
     */
    public Level(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Loads the level, generating obstacles, baskets, and rangers based on the level number.
     * 
     * @param level the level number to load
     * @return a LevelData object containing the obstacles, baskets, and rangers
     */
    public LevelData loadLevel(int level) {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        ArrayList<PicnicBasket> baskets = new ArrayList<>();
        ArrayList<Ranger> rangers = new ArrayList<>();
        Random random = new Random();

        int playerStartX = 50;
        int playerStartY = 50;

        int numObstacles = Math.min(MAX_OBSTACLES, 5 + level / 2);
        for (int i = 0; i < numObstacles; i++) {
            int x, y;
            int attempts = 0;

            do {
                x = random.nextInt((GRID_WIDTH / CELL_SIZE) - 2) * CELL_SIZE + CELL_SIZE;
                y = random.nextInt((GRID_HEIGHT / CELL_SIZE) - 2) * CELL_SIZE + CELL_SIZE;
                attempts++;
            } while ((isTooClose(obstacles, x, y, MIN_CLEARANCE) || 
                     (x == playerStartX && y == playerStartY)) && attempts < 100);

            if (attempts < 100) {
                obstacles.add(new Obstacle(x, y, "sprites/tree.png", 70, 70));
            }
        }

        int numBaskets = Math.min(MAX_BASKETS, 3 + level / 3);
        for (int i = 0; i < numBaskets; i++) {
            int x, y;
            int attempts = 0;

            do {
                x = random.nextInt((GRID_WIDTH / CELL_SIZE) - 2) * CELL_SIZE + CELL_SIZE;
                y = random.nextInt((GRID_HEIGHT / CELL_SIZE) - 2) * CELL_SIZE + CELL_SIZE;
                attempts++;
            } while (isTooClose(obstacles, x, y, MIN_CLEARANCE) && attempts < 100);

            if (attempts < 100) {
                baskets.add(new PicnicBasket(x, y, "sprites/basket.png", 60, 60));
            }
        }

        int numRangers = Math.min(MAX_RANGERS, 2 + level);
        for (int i = 0; i < numRangers; i++) {
            int x, y;
            int attempts = 0;

            do {
                x = random.nextInt((GRID_WIDTH / CELL_SIZE) - 2) * CELL_SIZE + CELL_SIZE;
                y = random.nextInt((GRID_HEIGHT / CELL_SIZE) - 2) * CELL_SIZE + CELL_SIZE;
                attempts++;
            } while ((isTooClose(obstacles, x, y, MIN_CLEARANCE) || 
                      isPlayerTooClose(playerStartX, playerStartY, x, y, PLAYER_CLEARANCE)) && attempts < 100);

            if (attempts < 100) {
                boolean verticalMovement = random.nextBoolean();

                rangers.add(new Ranger(x, y, "sprites/ranger.png", verticalMovement, 70, 70, gameEngine));
            }
        }

        return new LevelData(obstacles, baskets, rangers);
    }

    /**
     * Checks if a given position is too close to any obstacle.
     * 
     * @param obstacles the list of obstacles to check against
     * @param x the x-coordinate of the position to check
     * @param y the y-coordinate of the position to check
     * @param clearance the minimum clearance distance
     * @return true if the position is too close to an obstacle, false otherwise
     */
    private boolean isTooClose(ArrayList<Obstacle> obstacles, int x, int y, int clearance) {
        for (Obstacle obstacle : obstacles) {
            double distance = Math.sqrt(Math.pow(obstacle.x - x, 2) + Math.pow(obstacle.y - y, 2));
            if (distance < clearance) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player is too close to a given position.
     * 
     * @param playerX the player's x-coordinate
     * @param playerY the player's y-coordinate
     * @param x the x-coordinate of the position to check
     * @param y the y-coordinate of the position to check
     * @param clearance the minimum clearance distance
     * @return true if the player is too close to the position, false otherwise
     */
    private boolean isPlayerTooClose(int playerX, int playerY, int x, int y, int clearance) {
        double distance = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));
        return distance < clearance;
    }
}