package org.laykon.thebois.Utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.time.Duration;

public class ChallengeHandler implements Listener {

    DataHandler data = new DataHandler();

    @EventHandler
    public void onPlayerCatchFish(PlayerFishEvent it){
        if (it.getState() != PlayerFishEvent.State.CAUGHT_FISH){
            return;
        }
        data.reloadData();
        int status = data.incChallenge(ChallengeTypes.FISHING);
        if (status == 1){
            Title completionTitle = Title.title(
                    Component.text("§6Fishing §aChallenge has been Completed!"),
                    Component.text("§5Next goal: §6" + data.getChallengeGoal(ChallengeTypes.FISHING)),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
            );
            for (Player x : Bukkit.getOnlinePlayers()){
                x.showTitle(completionTitle);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity killedEntity = event.getEntity();
        if (!(killedEntity instanceof Monster)) {
            return;
        }
        Entity killerEntity = killedEntity.getKiller();
        if (killerEntity == null){
            return;
        }
        if (!(killerEntity instanceof Player)) {
            return;
        }
        data.reloadData();
        int status = data.incChallenge(ChallengeTypes.FIGHTING);
        if (status == 1){
            Title completionTitle = Title.title(
                    Component.text("§6Fighting §aChallenge has been Completed!"),
                    Component.text("§5Next goal: §6" + data.getChallengeGoal(ChallengeTypes.FISHING)),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
            );
            for (Player x : Bukkit.getOnlinePlayers()){
                x.showTitle(completionTitle);
            }
        }
    }
    @EventHandler
    public void onPlayerMine(BlockBreakEvent it){
        data.reloadData();
        int status = data.incChallenge(ChallengeTypes.MINING);
        if (status == 1){
            Title completionTitle = Title.title(
                    Component.text("§6Mining §aChallenge has been Completed!"),
                    Component.text("§5Next goal: §6" + data.getChallengeGoal(ChallengeTypes.MINING)),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
            );
            for (Player x : Bukkit.getOnlinePlayers()){
                x.showTitle(completionTitle);
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent it){
        data.reloadData();
        int status = data.incChallenge(ChallengeTypes.BUILDING);
        if (status == 1){
            Title completionTitle = Title.title(
                    Component.text("§6Building §aChallenge has been Completed!"),
                    Component.text("§5Next goal: §6" + data.getChallengeGoal(ChallengeTypes.BUILDING)),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
            );
            for (Player x : Bukkit.getOnlinePlayers()){
                x.showTitle(completionTitle);
            }
        }
    }
    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent it){
        data.reloadData();
        int status = data.incChallenge(ChallengeTypes.ACHIEVEMENTS);
        if (status == 1){
            Title completionTitle = Title.title(
                    Component.text("§6Achievement §aChallenge has been Completed!"),
                    Component.text("§5Next goal: §6" + data.getChallengeGoal(ChallengeTypes.ACHIEVEMENTS)),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
            );
            for (Player x : Bukkit.getOnlinePlayers()){
                x.showTitle(completionTitle);
            }
        }
    }

}
