package mug.bibus.smp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import java.io.File;
import java.util.Arrays;
import lombok.Getter;
import mug.bibus.smp.api.config.ConfigurationService;
import mug.bibus.smp.api.config.JsonConfigurationService;
import mug.bibus.smp.commands.GracePeriodCommand;
import mug.bibus.smp.commands.HomeCommand;
import mug.bibus.smp.configuration.CombatConfiguration;
import mug.bibus.smp.configuration.HomeConfiguration;
import mug.bibus.smp.listeners.CombatListener;
import mug.bibus.smp.listeners.PlayerListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SMPPlugin extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;

    private ConfigurationService configurationService;
    private CombatConfiguration combatConfiguration;
    private HomeConfiguration homeConfiguration;

    @Override
    public void onEnable() {
        configurationService = new JsonConfigurationService();
        combatConfiguration = configurationService.loadConfiguration(CombatConfiguration.class,
                new File(getDataFolder(), "combat.json"));
        homeConfiguration = configurationService.loadConfiguration(HomeConfiguration.class,
                new File(getDataFolder(), "homes.json"));

        liteCommands = LiteBukkitFactory.builder("smp", this)
                .commands(
                        new GracePeriodCommand(combatConfiguration),
                        new HomeCommand(homeConfiguration)
                )
                .build();

        Arrays.asList(
                new CombatListener(combatConfiguration),
                new PlayerListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        combatConfiguration.saveConfiguration();
        homeConfiguration.saveConfiguration();

        liteCommands.unregister();
    }

    public static SMPPlugin getInstance() {
        return JavaPlugin.getPlugin(SMPPlugin.class);
    }
}
