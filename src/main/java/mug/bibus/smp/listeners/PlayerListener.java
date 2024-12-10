package mug.bibus.smp.listeners;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mug.bibus.smp.configuration.WhitelistConfiguration;
import mug.bibus.smp.utilities.CC;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@RequiredArgsConstructor
public class PlayerListener implements Listener {
    private final WhitelistConfiguration whitelistConfiguration;

    @EventHandler
    public void onAsyncLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        if (!whitelistConfiguration.getWhitelistedPlayers().contains(uuid) && whitelistConfiguration.isWhitelistEnabled()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    Component.text("You are not whitelisted on this server, join ").color(CC.RED)
                            .append(Component.text("discord.gg/bibus").color(CC.PRIMARY)
                            .append(Component.text(" and request an invite.").color(CC.RED)))
            );
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        if (!player.hasPlayedBefore()) playerInventory.addItem(new ItemStack(Material.COOKED_BEEF, 32));
        event.joinMessage(Component.text(player.getName() + " has joined the server").color(CC.PRIMARY)
                .append(!player.hasPlayedBefore()
                        ? Component.text(" for the first time.").color(CC.PRIMARY)
                        : Component.text(".").color(CC.PRIMARY)));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(Component.text(player.getName() + " has quit the server.").color(CC.PRIMARY));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        event.leaveMessage(Component.text(player.getName() + " has quit the server.").color(CC.PRIMARY));
    }
}
