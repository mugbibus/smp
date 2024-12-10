package mug.bibus.smp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import java.util.Arrays;
import lombok.Getter;
import mug.bibus.smp.commands.GracePeriodCommand;
import mug.bibus.smp.handlers.CombatHandler;
import mug.bibus.smp.listeners.CombatListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SMPPlugin extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;

    private CombatHandler combatHandler;

    @Override
    public void onEnable() {
        combatHandler = new CombatHandler();

        liteCommands = LiteBukkitFactory.builder("smp", this)
                .commands(
                        new GracePeriodCommand(combatHandler)
                )
                .build();

        Arrays.asList(
                new CombatListener(combatHandler)
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
