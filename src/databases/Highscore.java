/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package databases;

/**
 *
 * @author xpn8tn
 */

public class Highscore {
    
    private final String name;
    private final int level;
    private final long timestamp;

    public Highscore(String name, int level, long timestamp) {
        this.name = name;
        this.level = level;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Highscore{" + "name=" + name + ", level=" + level + ", timestamp=" + timestamp + '}';
    }
}
