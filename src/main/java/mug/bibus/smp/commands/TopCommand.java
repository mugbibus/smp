package mug.bibus.smp.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.cooldown.Cooldown;
import dev.rollczi.litecommands.annotations.execute.Execute;
import java.time.temporal.ChronoUnit;
import mug.bibus.smp.utilities.CC;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(name = "top")
@Cooldown(key = "smp:top", count = 1, unit = ChronoUnit.HOURS)
public class TopCommand {
    @Execute
    public void executeTop(@Context Player player) {
        World world = player.getWorld();
        int topYCoordinate = world.getHighestBlockYAt(player.getLocation());

        if (world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END) {
            player.sendMessage(Component.text("You cannot use this command in this dimension.").color(CC.PRIMARY));
            return;
        }

        player.teleportAsync(new Location(world, player.getX(), topYCoordinate, player.getZ()))
                .thenAccept(success -> {
                    if (success)
                        player.sendMessage(Component.text("You have been teleported to the highest block of your Y coordinate.").color(CC.PRIMARY));
                });
    }
}
