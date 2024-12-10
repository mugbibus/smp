package mug.bibus.smp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import java.util.Arrays;
import lombok.Getter;
import mug.bibus.smp.commands.GracePeriodCommand;
import mug.bibus.smp.commands.HomeCommand;
import mug.bibus.smp.configuration.CombatConfiguration;
import mug.bibus.smp.configuration.HomeConfiguration;
import mug.bibus.smp.listeners.CombatListener;
import mug.bibus.smp.listeners.PlayerListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

@Getter
public class SMPPlugin extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;

    private HomeConfiguration homeConfiguration;
    private CombatConfiguration combatConfiguration;

    @Override
    public void onEnable() {
        homeConfiguration = ConfigAPI.init(
                HomeConfiguration.class,
                NameStyle.CAMEL_CASE,
                CommentStyle.INLINE,
                true,
                this
        );
        combatConfiguration = ConfigAPI.init(
                CombatConfiguration.class,
                NameStyle.CAMEL_CASE,
                CommentStyle.INLINE,
                true,
                this
        );

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
        liteCommands.unregister();
    }

    public static SMPPlugin getInstance() {
        return JavaPlugin.getPlugin(SMPPlugin.class);
    }
}
