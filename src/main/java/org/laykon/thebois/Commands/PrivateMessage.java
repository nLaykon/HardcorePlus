package org.laykon.thebois.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrivateMessage implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String name;
        Player player;
        if (!(sender instanceof Player)){
            name = "§8[§aPM§8]§r §8[§7Console§8]§r §a→ ";
        }else {
            player = (Player) sender;
            name = "§8[§aPM§8]§r §8[§7" + player.getName() + "§8]§r §a→ ";
        }
        if (!(args.length >= 1)){
            sender.sendMessage("§cYou must specify a player!");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            sender.sendMessage("§cInvalid Player!");
            return false;
        }

        if (!(args.length >= 2)){
            sender.sendMessage("§cYou need to specify a message!");
            return false;
        }
        StringBuilder sentString = new StringBuilder();
        sentString.append(name);
        sentString.append("§r§8[§7").append(target.getName()).append("§8]§r ");
        for (int i = 1; i < args.length; i++){
            sentString.append(args[i]);
            if (i < args.length - 1) {
                sentString.append(" ");
            }
        }

        target.sendMessage(String.valueOf(sentString));
        sender.sendMessage(String.valueOf(sentString));

        return true;
    }
}
