package mug.bibus.smp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import lombok.Getter;
import mug.bibus.smp.commands.GracePeriodCommand;
import mug.bibus.smp.handlers.CombatHandler;
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
    }

    @Override
    public void onDisable() {
        liteCommands.unregister();
    }

    public static SMPPlugin getInstance() {
        return JavaPlugin.getPlugin(SMPPlugin.class);
    }
}
