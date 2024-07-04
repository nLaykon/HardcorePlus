package org.laykon.thebois.Utilities;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinHandler implements Listener {

    DataHandler data = new DataHandler();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent it){
        if (data.countLives() <= 0){
            it.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
