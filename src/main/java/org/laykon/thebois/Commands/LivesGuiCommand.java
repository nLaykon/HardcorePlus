package org.laykon.thebois.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.laykon.thebois.Utilities.ChallengeTypes;
import org.laykon.thebois.Utilities.DataHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LivesGuiCommand implements CommandExecutor, Listener {
    DataHandler data = new DataHandler();

    public static Inventory livesInv;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }
        livesInv = Bukkit.createInventory(null, 36, "§cLife Challenges");

        ItemStack redPane = createItem(Material.RED_STAINED_GLASS_PANE, " ", "");

        ItemStack magentaPane = createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ", "");

        ItemStack miningItem = createItem(Material.DIAMOND_PICKAXE, "§7§lMining Challenge", "§7Challenge Progress\n\n§aCurrent: §6" + data.getCurrentProgress(ChallengeTypes.MINING) + "\n§bGoal: §6" + data.getChallengeGoal(ChallengeTypes.MINING));

        ItemStack buildingItem = createItem(Material.OAK_PLANKS, "§e§lBuilding Challenge", "§7Challenge Progress\n\n§aCurrent: §6" + data.getCurrentProgress(ChallengeTypes.BUILDING) + "\n§bGoal: §6" + data.getChallengeGoal(ChallengeTypes.BUILDING));

        ItemStack achievementItem = createItem(Material.TOTEM_OF_UNDYING, "§6§lAchievement Challenge", "§7Challenge Progress\n\n§aCurrent: §6" + data.getCurrentProgress(ChallengeTypes.ACHIEVEMENTS) + "\n§bGoal: §6" + data.getChallengeGoal(ChallengeTypes.ACHIEVEMENTS));

        ItemStack fishingItem = createItem(Material.FISHING_ROD, "§b§lFishing Challenge", "§7Challenge Progress\n\n§aCurrent: §6" + data.getCurrentProgress(ChallengeTypes.FISHING) + "\n§bGoal: §6" + data.getChallengeGoal(ChallengeTypes.FISHING));

        ItemStack fightingItem = createItem(Material.DIAMOND_SWORD, "§c§lFighting Challenge", "§7Challenge Progress\n\n§aCurrent: §6" + data.getCurrentProgress(ChallengeTypes.FIGHTING) + "\n§bGoal: §6" + data.getChallengeGoal(ChallengeTypes.FIGHTING));

        List<String> items = Arrays.asList(
                "x", "y", "x", "y", "x", "y", "x", "y", "x",
                "y", "x", "a", "x", "c", "x", "e", "x", "y",
                "x", "y", "x", "b", "x", "d", "x", "y", "x",
                "y", "x", "y", "x", "y", "x", "y", "x", "y");

        int index = 0;
        for (String item : items) {
            switch (item) {
                case "x":
                    livesInv.setItem(index, redPane);
                    break;
                case "y":
                    livesInv.setItem(index, magentaPane);
                    break;
                case "a":
                    livesInv.setItem(index, miningItem);
                    break;
                case "b":
                    livesInv.setItem(index, buildingItem);
                    break;
                case "c":
                    livesInv.setItem(index, achievementItem);
                    break;
                case "d":
                    livesInv.setItem(index, fishingItem);
                    break;
                case "e":
                    livesInv.setItem(index, fightingItem);
                    break;
                default:
                    // Handle unexpected cases or errors
                    break;
            }
            index++;
        }
        ((Player) sender).openInventory(livesInv);
        return true;
    }

    private ItemStack createItem(Material material, String displayName, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (displayName != null && !displayName.isEmpty()) {
            meta.setDisplayName(displayName);
        }
        if (lore != null && !lore.isEmpty()) {
            meta.setLore(Arrays.asList(lore.split("\n")));
        }
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(livesInv)) {
            event.setCancelled(true);
        }
    }
}
