package de.minnivini.betakey.Util;

import de.minnivini.betakey.BetaKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class lang {
    public static String getMessage(String message){
        File languageFolder = new File(BetaKey.getPlugin(BetaKey.class).getDataFolder() + "/language");
        String language = BetaKey.getPlugin(BetaKey.class).getLanguage();
        File langFile = new File(languageFolder, language + ".yml");
        if (!langFile.exists()) {
            return null;
        }
        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
        return langConfig.getString(message);


    }
    public static void createLanguageFolder() {
        File langFolder = new File(BetaKey.getPlugin(BetaKey.class).getDataFolder() + "/language");
        if (!langFolder.exists()) {
            langFolder.mkdir();
        }
        File enFile = new File(langFolder, "en.yml");
        File deFile = new File(langFolder, "de.yml");
        try {
            if (!enFile.exists()) {
                InputStream in = BetaKey.getPlugin(BetaKey.class).getResource("en.yml");
                Files.copy(in, enFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!deFile.exists()) {
                InputStream in = BetaKey.getPlugin(BetaKey.class).getResource("de.yml");
                Files.copy(in, deFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void checkLanguageUpdates() {
        File langFolder = new File(BetaKey.getPlugin(BetaKey.class).getDataFolder() + "/language");
        File enFile = new File(langFolder, "en.yml");
        File deFile = new File(langFolder, "de.yml");

        InputStream in = BetaKey.getPlugin(BetaKey.class).getResource("en.yml");
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        YamlConfiguration internalConfig = YamlConfiguration.loadConfiguration(reader);

        if (enFile.exists() ) {
            YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(enFile);

            if (langConfig.getString("version") == null || langConfig.getDouble("version") < internalConfig.getDouble("version")) {
                enFile.delete();
                deFile.delete();
                createLanguageFolder();

            }
        }

    }

}


