package org.laykon.thebois.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.TheBois;

public class NextReboot implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        long currentTime = System.currentTimeMillis();
        long timeRemaining = TheBois.nextRebootTime - currentTime; //ms

        if (timeRemaining > 0) {
            long hours = (timeRemaining / 1000) / 3600;
            long minutes = ((timeRemaining / 1000) % 3600) / 60;
            long seconds = (timeRemaining / 1000) % 60;

            sender.sendMessage("Next server reboot in " + hours + " hours, " + minutes + " minutes, and " + seconds + " seconds.");
        } else {
            sender.sendMessage("Next server reboot is imminent.");
        }
        return true;
    }

}
