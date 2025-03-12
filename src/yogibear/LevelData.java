/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;
import java.util.ArrayList;

/**
 *
 * @author xpn8tn
 */

public class LevelData {
    private ArrayList<Obstacle> obstacles;
    private ArrayList<PicnicBasket> baskets;
    private ArrayList<Ranger> rangers;

    /**
     * Constructs a new LevelData object with the specified obstacles, baskets, and rangers.
     * @param obstacles The list of obstacles in the level.
     * @param baskets The list of picnic baskets in the level.
     * @param rangers The list of rangers in the level.
     */
    public LevelData(ArrayList<Obstacle> obstacles, ArrayList<PicnicBasket> baskets, ArrayList<Ranger> rangers) {
        this.obstacles = obstacles;
        this.baskets = baskets;
        this.rangers = rangers;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<PicnicBasket> getBaskets() {
        return baskets;
    }

    public ArrayList<Ranger> getRangers() {
        return rangers;
    }
}
