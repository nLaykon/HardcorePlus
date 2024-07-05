package org.laykon.thebois.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPAccept implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!(TPA.tpRequests.containsKey(player))){
            player.sendMessage("§cYou have no TP Requests");
            return false;
        }
        Player target = TPA.tpRequests.get(player);
        if (target == null){
            sender.sendMessage("§aYou §cdenied §aa TPA requests!");
            TPA.tpRequests.remove(player);
            return true;
        }
        target.teleport(player.getLocation());
        player.sendMessage("§aTP Success");
        target.sendMessage("§aTP Success");
        TPA.tpRequests.remove(player);
        return true;
        //testcomment
    }
}
