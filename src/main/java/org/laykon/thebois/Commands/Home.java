package org.laykon.thebois.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.Utilities.DataHandler;

import java.util.*;

public class Home implements CommandExecutor {

    DataHandler data = new DataHandler();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;
        Map<String, Location> homes = data.getHomes(player.getUniqueId().toString());

        if (args.length < 1) {
            player.sendMessage("§aUsage: /home <list | add <name> | delete <name>>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "list":
                if (homes.isEmpty()){
                    player.sendMessage("§cYou have no homes!");
                } else {
                    player.sendMessage("§6Your homes:");
                    homes.keySet().forEach(home -> player.sendMessage("§6 - " + home));
                }
                break;
            case "add":
                if (args.length < 2) {
                    player.sendMessage("Usage: /home add <name>");
                    return false;
                }
                String addName = args[1];
                if (homes.containsKey(addName)){
                    player.sendMessage("§cYou already have a home named §6" + addName);
                } else {
                    data.addHome(player.getUniqueId().toString(), addName, player.getLocation());
                    player.sendMessage("§aYou successfully added a home named §6" + addName);
                }
                break;
            case "delete":
                if (args.length < 2) {
                    player.sendMessage("Usage: /home delete <name>");
                    return false;
                }
                String deleteName = args[1];
                if (!(homes.containsKey(deleteName))){
                    player.sendMessage("§cYou do not have a home named §6" + deleteName);
                } else {
                    data.deleteHome(player.getUniqueId().toString(), deleteName);
                    player.sendMessage("§aYou successfully deleted the home §6" + deleteName);
                }
                break;
            default:
                String homeName = args[0];
                if (homes == null || !homes.containsKey(homeName)) {
                    player.sendMessage("§cYou do not have a home named §6" + homeName);
                    break;
                }
                Location homeLocation = homes.get(homeName);
                if (homeLocation != null) {
                    player.teleport(homeLocation);
                    player.sendMessage("§aTeleported to your home §6" + homeName);
                } else {
                    player.sendMessage("§cFailed to teleport to home §6" + homeName + ". Please try again later.");
                }
                break;
        }

        return true;
    }
}

