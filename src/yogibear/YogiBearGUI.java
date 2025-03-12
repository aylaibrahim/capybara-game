/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * @author xpn8tn
 */

public class YogiBearGUI {
    private JFrame frame;
    private GameEngine gameArea;

    /**
     * Constructs the Yogi Bear game window and initializes the game area.
     */
    public YogiBearGUI() {
        frame = new JFrame("Capybara's Picnic Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
