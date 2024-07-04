package org.laykon.thebois.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.Utilities.DataHandler;

public class Hardcore implements CommandExecutor {

    DataHandler data = new DataHandler();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (ToggleHardcore.hardcore){
            sender.sendMessage("§aHardcore+ mode is §lEnabled");
        }else {
            sender.sendMessage("§cHardcore+ mode is §c§lDisabled");
        }
        return true;
    }
}
