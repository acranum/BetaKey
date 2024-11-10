package de.minnivini.betakey.Util;

import de.minnivini.betakey.BetaKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class lang {
    public String getMessage(String message){
        File languageFolder = new File(BetaKey.getPlugin(BetaKey.class).getDataFolder() + "/locales");
        String language = BetaKey.getPlugin(BetaKey.class).getLanguage();
        File langFile = new File(languageFolder, language + ".yml");
        if (!langFile.exists()) {
            return null;
        }
        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
        return langConfig.getString(message);


    }
    public void createLanguageFolder() {
        File langFolder = new File(BetaKey.getPlugin(BetaKey.class).getDataFolder() + "/locales");
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

}


