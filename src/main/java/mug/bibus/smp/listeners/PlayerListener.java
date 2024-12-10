package mug.bibus.smp.listeners;

import mug.bibus.smp.utilities.CC;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {
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
