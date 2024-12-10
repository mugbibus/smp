package mug.bibus.smp.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import mug.bibus.smp.configuration.CombatConfiguration;
import mug.bibus.smp.utilities.CC;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

@Command(name = "graceperiod")
@Permission("op")
@RequiredArgsConstructor
public class GracePeriodCommand {
    private final CombatConfiguration combatConfiguration;

    @Execute
    public void executeGracePeriod() {
        combatConfiguration.setGracePeriod(combatConfiguration.isGracePeriod());
        Bukkit.broadcast(combatConfiguration.isGracePeriod()
                ? Component.text("Grace Period has been disabled. You are now able to hit other players").color(CC.PRIMARY)
                : CC.GRACE_PERIOD_MESSAGE);
    }
}
