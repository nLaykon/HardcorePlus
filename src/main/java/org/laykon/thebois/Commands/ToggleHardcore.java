package org.laykon.thebois.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.Utilities.DataHandler;

public class ToggleHardcore implements CommandExecutor {
    DataHandler data = new DataHandler();

    public static boolean hardcore;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            System.out.println(player.getName());
            if (!player.getName().equalsIgnoreCase("nLaykon")){
                player.sendMessage("§cYou are not allowed to use this :(");
                return false;
            }
        }

        hardcore = data.readHardcore();

        data.toggleHardcore();

        if (hardcore){
            sender.sendMessage("§aHardcore+ has been §lEnabled");
        }else {
            sender.sendMessage("§aHardcore+ has been §c§lDisabled");
        }

        return true;
    }
}
