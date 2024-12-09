package mug.bibus.smp;

import org.bukkit.plugin.java.JavaPlugin;

public class SMPPlugin extends JavaPlugin {
    public static SMPPlugin getInstance() {
        return JavaPlugin.getPlugin(SMPPlugin.class);
    }
}
