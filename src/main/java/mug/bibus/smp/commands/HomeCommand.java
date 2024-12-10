package mug.bibus.smp.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import lombok.RequiredArgsConstructor;
import mug.bibus.smp.handlers.HomeHandler;
import mug.bibus.smp.utilities.CC;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "home")
@RequiredArgsConstructor
public class HomeCommand {
    private final HomeHandler homeHandler;

    @Execute
    public void executeHome(@Context Player player) {
        Location home = homeHandler.getHome(player);

        if (home == null) {
            player.sendMessage(Component.text("You do not have a Home setup, please use the ").color(CC.RED)
                    .append(Component.text("/home set").color(CC.PRIMARY))
                    .append(Component.text(" to set a Home location.").color(CC.RED)));
            return;
        }

        player.teleport(home);
        player.sendMessage(Component.text("You have been teleported to your Home.").color(CC.PRIMARY));
    }

    @Execute(name = "set")
    public void executeHomeSet() {

    }
}
