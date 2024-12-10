package mug.bibus.smp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import java.io.IOException;
import java.util.Arrays;
import lombok.Getter;
import mug.bibus.smp.commands.GracePeriodCommand;
import mug.bibus.smp.handlers.CombatHandler;
import mug.bibus.smp.handlers.HomeHandler;
import mug.bibus.smp.listeners.CombatListener;
import mug.bibus.smp.listeners.PlayerListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SMPPlugin extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;

    private CombatHandler combatHandler;
    private HomeHandler homeHandler;

    @Override
    public void onEnable() {
        combatHandler = new CombatHandler();
        homeHandler = new HomeHandler(this);

        liteCommands = LiteBukkitFactory.builder("smp", this)
                .commands(
                        new GracePeriodCommand(combatHandler)
                )
                .build();

        Arrays.asList(
                new CombatListener(combatHandler),
                new PlayerListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        liteCommands.unregister();

        try {
            homeHandler.getHomesConfig().save(homeHandler.getHomesFile());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static SMPPlugin getInstance() {
        return JavaPlugin.getPlugin(SMPPlugin.class);
    }
}
