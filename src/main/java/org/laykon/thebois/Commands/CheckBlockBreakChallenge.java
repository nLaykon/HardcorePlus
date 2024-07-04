package org.laykon.thebois.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.Utilities.ChallengeTypes;
import org.laykon.thebois.Utilities.DataHandler;

import javax.xml.crypto.Data;

public class CheckBlockBreakChallenge implements CommandExecutor {
    DataHandler data = new DataHandler();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(String.valueOf(data.getCurrentProgress(ChallengeTypes.MINING)));
        sender.sendMessage(String.valueOf(data.getChallengeGoal(ChallengeTypes.MINING)));
        return true;
    }
}
