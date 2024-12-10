package mug.bibus.smp.configuration;

import java.io.File;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import mug.bibus.smp.SMPPlugin;
import cc.invictusgames.ilib.configuration.StaticConfiguration;

@Getter @Setter
public class CombatConfiguration implements StaticConfiguration {
    private boolean gracePeriod = true;

    public void saveConfiguration() {
        try {
            SMPPlugin.getInstance().getConfigurationService().saveConfiguration(this,
                    new File(SMPPlugin.getInstance().getDataFolder(), "combat.json"));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
