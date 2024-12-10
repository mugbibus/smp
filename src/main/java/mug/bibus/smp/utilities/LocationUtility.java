package mug.bibus.smp.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtility {
    public static String parseToString(Location location) {
        return location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getYaw() + "," +
                location.getPitch() + "," +
                location.getWorld().getName();
    }

    public static Location parseToLocation(String string) {
        if (string == null) return null;
        String[] data = string.split(",");

        try {
            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            double z = Double.parseDouble(data[2]);
            float pitch = Float.parseFloat(data[4]);
            float yaw = Float.parseFloat(data[3]);
            World world = Bukkit.getWorld(data[5]);
            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException numberFormatException) {
            throw new RuntimeException(numberFormatException);
        }
    }
}
