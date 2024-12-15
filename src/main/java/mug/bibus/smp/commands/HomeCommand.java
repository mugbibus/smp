package mug.bibus.smp.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.cooldown.Cooldown;
import dev.rollczi.litecommands.annotations.execute.Execute;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import mug.bibus.smp.configuration.HomeConfiguration;
import mug.bibus.smp.utilities.CC;
import mug.bibus.smp.utilities.LocationUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "home")
@RequiredArgsConstructor
public class HomeCommand {
    private final HomeConfiguration homeConfiguration;

    @Execute
    @Cooldown(key = "smp:home", count = 30, unit = ChronoUnit.MINUTES)
    public void executeHome(@Context Player player) {
        Location home = LocationUtility.parseToLocation(homeConfiguration.getHomes().get(player.getUniqueId()));

        if (home == null) {
            player.sendMessage(Component.text("You do not have a Home setup, please use the ").color(CC.RED)
                    .append(Component.text("/home set").color(CC.PRIMARY))
                    .append(Component.text(" to set a Home location.").color(CC.RED)));
            return;
        }

        if (player.getWorld() != home.getWorld()) {
            player.sendMessage(Component.text("You cannot use this command when your ").color(CC.RED)
                    .append(Component.text(" Home location ").color(CC.PRIMARY))
                    .append(Component.text("is in a different world.").color(CC.RED)));
            return;
        }

        player.teleportAsync(home)
                .thenAccept(success -> {
                    if (success)
                        player.sendMessage(Component.text("You have been teleported to your Home.").color(CC.PRIMARY));
                });
    }

    @Execute(name = "set")
    public void executeHomeSet(@Context Player player) {
        homeConfiguration.getHomes().put(player.getUniqueId(), LocationUtility.parseToString(player.getLocation()));
        homeConfiguration.saveConfiguration();
        player.sendMessage(Component.text("You have set your Home location.").color(CC.PRIMARY));
    }
}
