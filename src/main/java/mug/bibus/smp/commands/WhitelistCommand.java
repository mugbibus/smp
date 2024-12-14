package mug.bibus.smp.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import mug.bibus.smp.configuration.WhitelistConfiguration;
import mug.bibus.smp.utilities.CC;
import mug.bibus.smp.utilities.UUIDUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@Command(name = "whitelist")
@Permission("smp.whitelist")
@RequiredArgsConstructor
public class WhitelistCommand {
    private final WhitelistConfiguration whitelistConfiguration;

    @Execute(name = "add")
    public void executeWhitelistAdd(@Context CommandSender commandSender, @Arg String playerName) {
        UUIDUtility.getFromMojang(playerName).thenAccept(uuid -> {
            whitelistConfiguration.getWhitelistedPlayers().add(uuid);
            whitelistConfiguration.saveConfiguration();

            commandSender.sendMessage(Component.text("You have whitelisted " + playerName + ".").color(CC.PRIMARY));
        });
    }

    @Execute(name = "remove")
    public void executeWhitelistRemove(@Context CommandSender commandSender, @Arg String playerName) {
        UUIDUtility.getFromMojang(playerName).thenAccept(uuid -> {
            if (!whitelistConfiguration.getWhitelistedPlayers().contains(uuid)) {
                commandSender.sendMessage(Component.text(playerName).color(CC.PRIMARY)
                        .append(Component.text(" is not whitelisted.").color(CC.RED)));
                return;
            }

            whitelistConfiguration.getWhitelistedPlayers().remove(uuid);
            whitelistConfiguration.saveConfiguration();

            commandSender.sendMessage(Component.text("You have un-whitelisted " + playerName + ".").color(CC.PRIMARY));
        });
    }
}
