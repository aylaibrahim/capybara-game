/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibear;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author xpn8tn
 */

public class GameEngine extends JPanel {
    private final int FPS = 60;
    private Player yogi;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<PicnicBasket> baskets;
    private ArrayList<Ranger> rangers;
    private Timer timer;
    private boolean gameOver;
    private int level = 1;
    private int lives = 3;
    private long startTime;
    private Level levelManager;

    private JLabel levelLabel;
    private JLabel livesLabel;
    private JLabel timeLabel;

    /**
     * Initializes the game engine, setting up the game state and UI elements.
     */
    public GameEngine() {
        setLayout(new BorderLayout());

        levelManager = new Level(this);
        obstacles = new ArrayList<>();
        baskets = new ArrayList<>();
        rangers = new ArrayList<>();

        generateLevel(level);

        yogi = new Player(50, 50, "sprites/yogi.png", this);

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        configureInputActions();

        timer = new Timer(1000 / FPS, e -> updateGame());
        timer.start();

        startTime = System.currentTimeMillis();
    }

    /**
     * Creates and returns the top panel containing the menu and game stats.
     * @return The top panel.
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        JComboBox<String> menuDropdown = new JComboBox<>(new String[]{"Menu", "Restart"});
        menuDropdown.addActionListener(e -> {
            String selected = (String) menuDropdown.getSelectedItem();
            if ("Restart".equals(selected)) {
                restartGame();
            }
        });

        topPanel.add(menuDropdown);

        levelLabel = new JLabel("Level: " + level);
        livesLabel = new JLabel("Lives: " + lives);
        timeLabel = new JLabel("Time: 0s");

        topPanel.add(levelLabel);
        topPanel.add(livesLabel);
        topPanel.add(timeLabel);

        return topPanel;
    }

    /**
     * Configures input actions for player movement.
     */
    private void configureInputActions() {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("W"), "move up");
        actionMap.put("move up", new MoveAction(0, -5));

        inputMap.put(KeyStroke.getKeyStroke("A"), "move left");
        actionMap.put("move left", new MoveAction(-5, 0));

        inputMap.put(KeyStroke.getKeyStroke("S"), "move down");
        actionMap.put("move down", new MoveAction(0, 5));

        inputMap.put(KeyStroke.getKeyStroke("D"), "move right");
        actionMap.put("move right", new MoveAction(5, 0));
    }

    /**
     * Restarts the game, resetting all variables and reinitializing the player and game state.
     */
    private void restartGame() {
        level = 1;
        lives = 3;
        gameOver = false;
        startTime = System.currentTimeMillis();
        generateLevel(level);
        yogi.resetPosition();
        yogi.setVelocity(0, 0);
        configureInputActions();
        timer.restart(); 
        repaint();
    }

    /**
     * Generates there specified level, loading obstacles, baskets, and rangers.
     * @param level The level to generate.
     */
    private void generateLevel(int level) {
        LevelData data = levelManager.loadLevel(level);
        obstacles = data.getObstacles();
        baskets = data.getBaskets();
        rangers = data.getRangers();
    }

    /**
     * Updates the game state, moving the player and rangers, checking collisions, and repainting the screen.
     */
    private void updateGame() {
        yogi.move();
        checkCollisions();
        updateStats();
        repaint();

        if (gameOver) {
            timer.stop();
            showGameOverScreen();
        }
    }

    /**
     * Updates the game statistics, including level, lives, and elapsed time.
     */
    private void updateStats() {
        levelLabel.setText("Level: " + level);
        livesLabel.setText("Lives: " + lives);
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        timeLabel.setText("Time: " + elapsedTime + "s");
    }

    /**
     * Checks for collisions between the player, rangers, and baskets, handling game over and level progression.
     */
    private void checkCollisions() {
        for (Ranger ranger : rangers) {
            if (yogi.collides(ranger)) {
                lives--;
                if (lives <= 0) {
                    gameOver = true;
                } else {
                    yogi.resetPosition();
                }
            }
        }

        baskets.removeIf(basket -> yogi.collides(basket));

        if (baskets.isEmpty()) {
            if (level < 10) {
                level++;
                resetPlayerPosition();
                generateLevel(level);
            } else {
                timer.stop();
                showWinningScreen();
            }
        }
    }

    /**
     * Displays the game over screen and prompts the player to enter their name for the record.
     */
    private void showGameOverScreen() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        String playerName = JOptionPane.showInputDialog(
                this,
                "Game Over!\n" +
                        "You reached Level " + level + " and survived for " + elapsedTime + " seconds.\n" +
                        "Enter your name to save your record:"
        );

        if (playerName != null && !playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thank you, " + playerName + "! Your record has been saved.");
        } else {
            JOptionPane.showMessageDialog(this, "No name entered. Record not saved.");
        }

        System.exit(0);
    }

    /**
     * Displays the winning screen and prompts the player to enter their name for the record.
     */
    private void showWinningScreen() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        String playerName = JOptionPane.showInputDialog(
                this,
                "Congratulations! You completed all 10 levels in " + elapsedTime + " seconds.\n" +
                        "Enter your name to save your record:"
        );

        if (playerName != null && !playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thank you, " + playerName + "! Your record has been saved.");
        } else {
            JOptionPane.showMessageDialog(this, "No name entered. Record not saved.");
        }

        System.exit(0);
    }

    /**
     * Paints the game components on the screen, including the background, player, obstacles, baskets, and rangers.
     * @param g The graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(new ImageIcon("sprites/background.jpg").getImage(), 0, 30, 800, 570, null);

        yogi.draw(g);
        for (Obstacle obstacle : obstacles) obstacle.draw(g);
        for (PicnicBasket basket : baskets) basket.draw(g);
        for (Ranger ranger : rangers) ranger.draw(g);
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
    
    /**
     * Action class to move the player based on key inputs.
     */
    private class MoveAction extends AbstractAction {
        private int dx, dy;

        public MoveAction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            yogi.setVelocity(dx, dy);
        }
    }
    
    /**
     * Class to reset the player position.
     */
    public void resetPlayerPosition() {
        yogi.setPosition(50, 50);
    }
}