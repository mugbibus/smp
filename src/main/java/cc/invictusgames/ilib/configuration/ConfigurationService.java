package cc.invictusgames.ilib.configuration;

import java.io.File;
import java.io.IOException;

// Taken from https://github.com/InvictusGames/iLib
public interface ConfigurationService {
    void saveConfiguration(StaticConfiguration configuration, File file) throws IOException;
    <T extends StaticConfiguration> T loadConfiguration(Class<? extends T> clazz, File file);
}