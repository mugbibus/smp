package mug.bibus.smp.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import mug.bibus.smp.SMPPlugin;
import mug.bibus.smp.api.config.StaticConfiguration;

@Getter
public class HomeConfiguration implements StaticConfiguration {
    private Map<UUID, String> homes = new HashMap<>();

    public void saveConfiguration() {
        try {
            SMPPlugin.getInstance().getConfigurationService().saveConfiguration(this,
                    new File(SMPPlugin.getInstance().getDataFolder(), "homes.json"));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
