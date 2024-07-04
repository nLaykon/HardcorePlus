package org.laykon.thebois;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.laykon.thebois.Commands.*;
import org.laykon.thebois.Utilities.*;

import java.time.Duration;

public final class TheBois extends JavaPlugin {
    private DataHandler data;

    private static TheBois instance;

    public static TheBois getInstance() {
        return instance;
    }

    public static long nextRebootTime = System.currentTimeMillis() + (12 * ((1000 * 60) * 60));

    static boolean tenMin = false;
    static boolean fiveMin = false;
    static boolean oneMin = false;
    static boolean fifteenSec = false;

    public static int lives = 1;

    public static ScoreboardManager manager;
    public static Scoreboard board;
    public static Objective objective;

    @Override
    public void onEnable() {
        instance = this;
        data = new DataHandler();

        cmd("ping", new Ping());
        cmd("tpa", new TPA());
        cmd("tpaccept", new TPAccept());
        cmd("nextreboot", new NextReboot());
        cmd("home", new Home());
        cmd("hardcore", new Hardcore());
        cmd("togglehardcore", new ToggleHardcore());
        cmd("dev/lives/add", new AddLife());
        cmd("dev/lives/remove", new RemoveLife());
        cmd("lives", new LivesGuiCommand());
        cmd("dev/challenges/mining", new CheckBlockBreakChallenge());

        event(new HardCorePlusEvent());
        event(new JoinHandler());
        event(new ChallengeHandler());
        event(new LivesGuiCommand());


        ToggleHardcore.hardcore = data.readHardcore();

        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        objective = board.registerNewObjective("Lives", "dummy", ChatColor.RED + "Lives");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        getServer().getScheduler().runTaskTimer(this, ScoreboardHandler::updateScoreboard, 0L, 20L);


        new BukkitRunnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() >= (nextRebootTime - (15 * 1000)) && !fifteenSec){
                    Title tenMTitle = Title.title(
                            Component.text("§6Server Restart"),
                            Component.text("§cRestarting in 15 seconds"),
                            Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
                    );
                    for (Player x : Bukkit.getOnlinePlayers()){
                        x.showTitle(tenMTitle);
                    }
                    fifteenSec = true;
                }
                if (System.currentTimeMillis() >= nextRebootTime - (1000 * 60) && !oneMin){
                    Title tenMTitle = Title.title(
                            Component.text("§6Server Restart"),
                            Component.text("§cRestarting in 1 minute"),
                            Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
                    );
                    for (Player x : Bukkit.getOnlinePlayers()){
                        x.showTitle(tenMTitle);
                    }
                    oneMin = true;
                }
                if (System.currentTimeMillis() >= nextRebootTime - (5 * (1000 * 60)) && !fiveMin){
                    Title tenMTitle = Title.title(
                            Component.text("§6Server Restart"),
                            Component.text("§cRestarting in 5 minutes"),
                            Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
                    );
                    for (Player x : Bukkit.getOnlinePlayers()){
                        x.showTitle(tenMTitle);
                    }
                    fiveMin = true;
                }
                if (System.currentTimeMillis() >= nextRebootTime - (10 * (1000 * 60)) && !tenMin){
                    Title tenMTitle = Title.title(
                            Component.text("§6Server Restart"),
                            Component.text("§cRestarting in 10 minutes"),
                            Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1))
                    );
                    for (Player x : Bukkit.getOnlinePlayers()){
                        x.showTitle(tenMTitle);
                    }
                    tenMin = true;
                }
                if (System.currentTimeMillis() >= nextRebootTime){

                    for (Player x : Bukkit.getOnlinePlayers()){
                        x.kick(Component.text("§aServer Rebooting..."));
                    }


                    Bukkit.shutdown();
                }
            }
        }.runTaskTimer(instance, 10, 10);
        System.out.println(data.getLoaded());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        data.reloadData();;
    }

    public void cmd(String name, CommandExecutor command) {
        getCommand(name).setExecutor(command);
    }

    public static void executeCmd(String command) {
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        System.out.println("Command Executed: " + command);
    }

    public void event(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
