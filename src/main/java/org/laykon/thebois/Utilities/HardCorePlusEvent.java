package org.laykon.thebois.Utilities;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Duration;

public class HardCorePlusEvent implements Listener {
    DataHandler data = new DataHandler();
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent it){
        if (!data.readHardcore()){
            return;
        }

        data.removeLife();

        if (data.countLives() >= 1){
            Title deathTitle = Title.title(
                    Component.text("§6" + it.getPlayer().getName() + " §cHas died!"),
                    Component.text("§5Remaining Lives: §6" + data.countLives()),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
            );
            for (Player x : Bukkit.getOnlinePlayers()){
                x.showTitle(deathTitle);
            }
            return;
        }


        it.setCancelled(true);
        Title deathTitle = Title.title(
                Component.text("§6" + it.getPlayer().getName() + " §cHas died!"),
                Component.text("§5Bye bye world :("),
                Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
        );
        for (Player x : Bukkit.getOnlinePlayers()){
            x.showTitle(deathTitle);
            x.setGameMode(GameMode.SPECTATOR);
        }
    }

}
