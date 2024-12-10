package cc.invictusgames.ilib.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import mug.bibus.smp.SMPPlugin;

// Taken from https://github.com/InvictusGames/iLib
public class JsonConfigurationService implements ConfigurationService {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    @Override
    public void saveConfiguration(StaticConfiguration configuration, File file) {
        Logger smpPluginLogger = SMPPlugin.getInstance().getLogger();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            gson.toJson(configuration, outputStreamWriter);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException ioException) {
            smpPluginLogger.warning("Failed to save configuration " + configuration.getClass().getName() + " to file " + file.getName());
        }
    }

    @Override
    public <T extends StaticConfiguration> T loadConfiguration(Class<? extends T> clazz, File file) {
        Logger smpPluginLogger = SMPPlugin.getInstance().getLogger();

        if ((!file.getParentFile().exists()) && (!file.getParentFile().mkdir())) {
            smpPluginLogger.warning("Failed to create parent folder for " + file.getName());
            return null;
        }

        try {
            T config = clazz. getDeclaredConstructor().newInstance();
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    smpPluginLogger.warning("Failed to create file for " + file.getName());
                    return null;
                }
                saveConfiguration(config, file);
            }
        } catch (InstantiationException
                 | IllegalAccessException
                 | IOException
                 | InvocationTargetException
                 | NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }

        try {
            T config = gson.fromJson(new BufferedReader(new FileReader(file)), clazz);
            saveConfiguration(config, file);
            return config;
        } catch (FileNotFoundException fileNotFoundException) {
            smpPluginLogger.warning("Failed to load configuration " + clazz.getName() + " from file " + file.getName());
            return null;
        }
    }
}