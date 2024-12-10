package mug.bibus.smp.listeners;

import lombok.RequiredArgsConstructor;
import mug.bibus.smp.configuration.CombatConfiguration;
import mug.bibus.smp.utilities.CC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class CombatListener implements Listener {
    private final CombatConfiguration combatConfiguration;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(CC.GRACE_PERIOD_MESSAGE);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity attacker = event.getDamager();

        if (victim.getType() != EntityType.PLAYER || attacker.getType() != EntityType.PLAYER) return;

        if (combatConfiguration.isGracePeriod()) {
            attacker.sendMessage(CC.GRACE_PERIOD_MESSAGE);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (!(projectile.getShooter() instanceof Player)) return;

        Player shooter = (Player) projectile.getShooter();

        if (!(event.getHitEntity() instanceof Player)) return;

        if (combatConfiguration.isGracePeriod()) {
            shooter.sendMessage(CC.GRACE_PERIOD_MESSAGE);
            event.setCancelled(true);
        }
    }
}
