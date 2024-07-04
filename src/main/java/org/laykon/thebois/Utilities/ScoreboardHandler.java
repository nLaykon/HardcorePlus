package org.laykon.thebois.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.laykon.thebois.TheBois;

public class ScoreboardHandler {
    static DataHandler data = new DataHandler();
    public static void updateScoreboard(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            int lives = data.countLives();
            Score score = TheBois.objective.getScore("");
            score.setScore(lives);
            player.setScoreboard(TheBois.board);
        }
    }
}
