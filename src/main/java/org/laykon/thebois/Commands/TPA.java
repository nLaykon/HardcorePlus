package org.laykon.thebois.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TPA implements CommandExecutor {
    public static Map<Player, Player> tpRequests = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            System.err.printf("Invalid player: %s%n", args[0]);
            return false;
        }
        if (player.getName().equals(target.getName())){
            player.sendMessage("§cYou cannot TPA to yourself!");
            return false;
        }

        tpRequests.put(target, player);

        player.sendMessage("§aTeleport request sent to §6" + target.getName());
        target.sendMessage("§aRecieved a Teleport Request from §6" + player.getName());

        return true;
    }
}
