package databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Highscores {

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    public Highscores(int maxScores) throws SQLException {
        this.maxScores = maxScores;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");
        connectionProps.put("serverTimezone", "UTC");
        
        // Assuming MySQL is running on port 3307 for your database
        String dbURL = "jdbc:mysql://localhost:3307/highscores";  
        connection = DriverManager.getConnection(dbURL, connectionProps);
        
        String insertQuery = "INSERT INTO HIGHSCORES (TIMESTAMP, NAME, LEVELS) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);

        String deleteQuery = "DELETE FROM HIGHSCORES WHERE LEVELS=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public ArrayList<Highscore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES ORDER BY LEVELS DESC, TIMESTAMP ASC";
        ArrayList<Highscore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int level = results.getInt("LEVELS");
            long timestamp = results.getTimestamp("TIMESTAMP").getTime();
            highScores.add(new Highscore(name, level, timestamp));
        }
        return highScores;
    }

    public void putHighScore(String playerName, int level, long timestamp) throws SQLException {
        ArrayList<Highscore> highScores = getHighScores();
        if (highScores.size() < maxScores) {
            insertScore(playerName, level, timestamp);
        } else {
            Highscore lowestScore = highScores.get(highScores.size() - 1);
            if (lowestScore.getLevel() < level || (lowestScore.getLevel() == level && lowestScore.getTimestamp() > timestamp)) {
                deleteScores(lowestScore.getLevel());
                insertScore(playerName, level, timestamp);
            }
        }
    }

    private void insertScore(String name, int level, long timestamp) throws SQLException {
        Timestamp ts = new Timestamp(timestamp);
        insertStatement.setTimestamp(1, ts);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, level);
        insertStatement.executeUpdate();
    }

    private void deleteScores(int level) throws SQLException {
        deleteStatement.setInt(1, level);
        deleteStatement.executeUpdate();
    }
}
