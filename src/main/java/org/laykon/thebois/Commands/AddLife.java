package org.laykon.thebois.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.Utilities.DataHandler;

public class AddLife implements CommandExecutor {
    DataHandler data = new DataHandler();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player){
            sender.sendMessage("§cThis is a development command, you cannot use it!");
            return false;
        }


        data.addLife();
        return true;
    }
}
