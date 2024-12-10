package mug.bibus.smp.configuration;

import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.ConfigName;

@ConfigName("combat.yml")
public interface CombatConfiguration extends Config {
    default boolean isGracePeriod() {
        return true;
    }

    void setGracePeriod(boolean gracePeriod);
}
