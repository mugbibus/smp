package mug.bibus.smp.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.ConfigName;

@ConfigName("homes.yml")
public interface HomeConfiguration extends Config {
    default Map<UUID, String> getHomes() {
        return new HashMap<>();
    }
}
