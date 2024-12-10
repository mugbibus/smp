package mug.bibus.smp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import java.io.File;
import java.util.Arrays;
import lombok.Getter;
import cc.invictusgames.ilib.configuration.ConfigurationService;
import cc.invictusgames.ilib.configuration.JsonConfigurationService;
import mug.bibus.smp.commands.GracePeriodCommand;
import mug.bibus.smp.commands.HomeCommand;
import mug.bibus.smp.commands.TopCommand;
import mug.bibus.smp.commands.WhitelistCommand;
import mug.bibus.smp.configuration.CombatConfiguration;
import mug.bibus.smp.configuration.HomeConfiguration;
import mug.bibus.smp.configuration.WhitelistConfiguration;
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
    private WhitelistConfiguration whitelistConfiguration;

    @Override
    public void onEnable() {
        configurationService = new JsonConfigurationService();
        combatConfiguration = configurationService.loadConfiguration(CombatConfiguration.class,
                new File(getDataFolder(), "combat.json"));
        homeConfiguration = configurationService.loadConfiguration(HomeConfiguration.class,
                new File(getDataFolder(), "homes.json"));
        whitelistConfiguration = configurationService.loadConfiguration(WhitelistConfiguration.class,
                new File(getDataFolder(), "whitelist.json"));

        liteCommands = LiteBukkitFactory.builder("smp", this)
                .commands(
                        new GracePeriodCommand(combatConfiguration),
                        new HomeCommand(homeConfiguration),
                        new TopCommand(),
                        new WhitelistCommand(whitelistConfiguration)
                )
                .build();

        Arrays.asList(
                new CombatListener(combatConfiguration),
                new PlayerListener(whitelistConfiguration)
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
