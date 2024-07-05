package org.laykon.thebois.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPDeny implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }
        Player player = (Player)sender;
        if (!(TPA.tpRequests.containsKey(player))){
            player.sendMessage("§cYou do not have any TPA requests");
            return false;
        }
        Player target = TPA.tpRequests.get(player);
        if (target == null){
            sender.sendMessage("§aYou §cdenied §aa TPA requests!");
            TPA.tpRequests.remove(player);
            return true;
        }
        target.sendMessage("§6" + player.getName() + " §aHas denied your tp request!");
        sender.sendMessage("§aYou §cdenied §aa TPA requests from §6" + target.getName() + "§a!");
        TPA.tpRequests.remove(player);
        return true;
    }
}
