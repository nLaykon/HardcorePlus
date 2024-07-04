package org.laykon.thebois.Utilities;

import org.bukkit.Location;
import org.bukkit.block.data.type.Switch;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.laykon.thebois.Commands.ToggleHardcore;
import org.laykon.thebois.TheBois;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataHandler {
    YamlConfiguration data;
    public final JavaPlugin plugin;

    public DataHandler() {
        this.plugin = TheBois.getInstance();
        loadConfig();
    }

    private void loadConfig() {
        File dataFile = new File(plugin.getDataFolder(), "data.yml");

        if (!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
        }

        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void reloadData() {
        File dataFile = new File(plugin.getDataFolder(), "data.yml");
        data = YamlConfiguration.loadConfiguration(dataFile);
        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning("Failed to reload config.yml: " + e.getMessage());
        }
    }

    public YamlConfiguration getConfig() {
        return data;
    }

    public void addOrCreate(String path, Object value) {
        getConfig().set(path, value);

        try {
            getConfig().save(new File(plugin.getDataFolder(), "data.yml"));
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save data.yml: " + e.getMessage());
        }
    }

    public Object readValue(String path) {
        Object value = getConfig().get(path);
        if (!(value == null)) {
            return value;
        }
        return null;
    }

    public Map<String, Location> getHomes(String uuid) {
        Object homesObj = data.get("Player Data." + uuid + ".homes");
        if (homesObj != null) {
        } else {
            return new HashMap<>();
        }

        // Check if homesObj is a MemorySection
        if (!(homesObj instanceof MemorySection)) {
            System.out.println("homesObj is not a MemorySection.");
            return new HashMap<>();
        }

        MemorySection homesSection = (MemorySection) homesObj;
        Map<String, Object> homesStringMap = homesSection.getValues(false);
        Map<String, Location> homesMap = new HashMap<>();

        homesStringMap.forEach((name, locString) -> {
            if (locString instanceof String) {
                Location loc = locationFromString((String) locString);
                if (loc != null) {
                    homesMap.put(name, loc);
                } else {
                    System.out.println("Failed to parse location string: " + locString);
                }
            } else {
                System.out.println("locString is not a String: " + locString.getClass().getName());
            }
        });

        return homesMap;
    }


    public void addHome(String uuid, String name, Location loc) {
        Map<String, Location> homes = getHomes(uuid);
        homes.put(name, loc);

        Map<String, String> homesStringMap = new HashMap<>();
        homes.forEach((homeName, location) -> {
            homesStringMap.put(homeName, locationToString(location));
        });

        data.set("Player Data." + uuid + ".homes", homesStringMap);
        saveDataFile();
        reloadData();
    }

    public void deleteHome(String uuid, String name) {
        Map<String, Location> homes = getHomes(uuid);
        if (homes.containsKey(name)) {
            homes.remove(name);

            // Convert locations to strings for storage
            Map<String, String> homesStringMap = new HashMap<>();
            homes.forEach((homeName, location) -> {
                homesStringMap.put(homeName, locationToString(location));
            });

            data.set("Player Data." + uuid + ".homes", homesStringMap);
            saveDataFile();
            reloadData();
        }
    }
    public boolean readHardcore(){
        reloadData();
        Object check = data.get("server.options.hardcore");
        if (!(check instanceof Boolean)){
            return false;
        }

        return (boolean) check;
    }

    public void toggleHardcore(){
        ToggleHardcore.hardcore = !ToggleHardcore.hardcore;
        data.set("server.options.hardcore", ToggleHardcore.hardcore);
        saveDataFile();
        reloadData();
    }

    public void addLife(){
        reloadData();
        Object check = data.get("server.lives");
        if (!(check instanceof Integer)){
            return;
        }
        int lives = (int) check;
        lives += 1;
        data.set("server.lives", lives);
        saveDataFile();
        reloadData();
    }
    public int countLives(){
        reloadData();
        Object check = data.get("server.lives");
        if (!(check instanceof Integer)){
            return 0;
        }

        return (int) check;
    }
    public void removeLife(){
        reloadData();
        Object check = data.get("server.lives");
        if (!(check instanceof Integer)){
            return;
        }
        int lives = (int) check;
        lives -= 1;
        data.set("server.lives", lives);
        saveDataFile();
        reloadData();
    }

    public int getChallengeGoal(ChallengeTypes x) {
        reloadData();

        Object check = data.get("server.challenges." + x.getName() + ".completed");
        if (!(check instanceof Integer)) {
            return -1;
        }

        int timesReached = (int) check;
        int minDifficulty = x.getMinDifficulty();
        int maxDifficulty = x.getMaxDifficulty();

        reloadData();
        return calculateNextGoal(timesReached, minDifficulty, maxDifficulty);
    }
    public int incChallenge(ChallengeTypes x) {
        reloadData();
        Object check = data.get("server.challenges." + x.getName() + ".current");

        if (!(check instanceof Integer)) {
            System.out.println("Not int");
            return -1;
        }

        int progress = (int) check;
        progress ++;
        data.set("server.challenges." + x.getName() + ".current", progress);
        saveDataFile();

        int goal = getChallengeGoal(x);

        if (progress >= goal) {
            completeChallenge(x);
            saveDataFile();
            return 1;
        }

        reloadData();
        return 0;
    }

    public void completeChallenge(ChallengeTypes x) {
        reloadData();
        Object check = data.get("server.challenges." + x.getName() + ".completed");

        if (!(check instanceof Integer)) {
            System.out.println("Not int");
            return;
        }

        int completed = (int) check;
        completed++;
        data.set("server.challenges." + x.getName() + ".completed", completed);

        data.set("server.challenges." + x.getName() + ".current", (int) 1);
        saveDataFile();
        addLife();
        reloadData();
    }


    public int getCurrentProgress(ChallengeTypes x){
        reloadData();
        Object check = data.get("server.challenges." + x.getName() + ".current");
        if (!(check instanceof Integer)) {
            return -1;
        }
        return (int) check;
    }

    private int calculateNextGoal(int timesReached, int minDifficulty, int maxDifficulty) {
        double t = (double) timesReached;
        double nextGoal = minDifficulty + (maxDifficulty - minDifficulty) * (t / (t + 1));

        return (int) Math.round(nextGoal);
    }


    private String locationToString(Location loc) {
        if (loc == null) {
            return null;
        }
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }

    private Location locationFromString(String locString) {
        if (locString == null || locString.isEmpty()) {
            return null;
        }

        String[] parts = locString.split(",");
        if (parts.length < 6) {
            return null;
        }

        try {
            String worldName = parts[0];
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);

            return new Location(plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);
        } catch (Exception e) {
            plugin.getLogger().warning("Error parsing location string: " + locString);
            return null;
        }
    }

    private void saveDataFile() {
        File dataFile = new File(plugin.getDataFolder(), "data.yml");
        try {
            data.save(dataFile);
            System.out.println("Data saved to file");
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save data.yml: " + e.getMessage());
        }
    }



    public String getLoaded() {
        return getConfig().getString("server.dataLoadedMessage");
    }
}
