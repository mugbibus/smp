package mug.bibus.smp.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import mug.bibus.smp.SMPPlugin;
import mug.bibus.smp.api.config.StaticConfiguration;

@Getter
public class WhitelistConfiguration implements StaticConfiguration {
    private boolean whitelistEnabled = true;
    private List<UUID> whitelistedPlayers = new ArrayList<>();

    public void saveConfiguration() {
        try {
            SMPPlugin.getInstance().getConfigurationService().saveConfiguration(this,
                    new File(SMPPlugin.getInstance().getDataFolder(), "whitelist.json"));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
