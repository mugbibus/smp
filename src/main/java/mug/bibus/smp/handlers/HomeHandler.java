package mug.bibus.smp.handlers;

import java.io.File;
import java.io.IOException;
import mug.bibus.smp.SMPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class HomeHandler {
    private final SMPPlugin smpPlugin;

    private final File homesFile;
    private final YamlConfiguration homesConfig;

    public HomeHandler(SMPPlugin smpPlugin) {
        this.smpPlugin = smpPlugin;
        homesFile = new File(smpPlugin.getDataFolder(), "homes.yml");

        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }

        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
    }

    public void saveHome(Player player, Location location) {
        String path = "homes." + player.getUniqueId();

        homesConfig.set(path + ".world", location.getWorld().getName());
        homesConfig.set(path + ".x", location.getX());
        homesConfig.set(path + ".y", location.getY());
        homesConfig.set(path + ".z", location.getZ());
        homesConfig.set(path + ".pitch", location.getPitch());
        homesConfig.set(path + ".yaw", location.getYaw());

        try {
            homesConfig.save(homesFile);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public Location getHome(Player player) {
        String path = "homes." + player.getUniqueId();
        if (!homesConfig.contains(path)) return null;

        String worldName = homesConfig.getString(path + ".world");
        if (worldName == null) return null;

        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;

        double x = homesConfig.getDouble(path + ".x");
        double y = homesConfig.getDouble(path + ".y");
        double z = homesConfig.getDouble(path + ".z");
        float pitch = (float) homesConfig.getDouble(path + ".pitch");
        float yaw = (float) homesConfig.getDouble(path + ".yaw");

        return new Location(world, x, y, z, yaw, pitch);
    }
}
