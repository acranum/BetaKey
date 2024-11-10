package de.minnivini.betakey;

import de.minnivini.betakey.Commands.BetakeyCMD;
import de.minnivini.betakey.Util.licence;
import de.minnivini.betakey.Util.lang;
import de.minnivini.betakey.listener.onPlayerJoin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class BetaKey extends JavaPlugin {
    lang lang = new lang();

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        lang.createLanguageFolder();

        if (!betaCheack()) {
            getLogger().info(lang.getMessage("BetaDis"));
        }
        getCommand("betakey").setExecutor(new BetakeyCMD());
        getCommand("license").setExecutor(new licence());


        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
    }


    public boolean betaCheack() {
        Boolean beta = config.getBoolean("beta");
        if (beta.equals(false)) {
            return false;
        }
        return true;
    }

    public void reaload() {
        saveDefaultConfig();
        reloadConfig();
    }
    public String getLanguage() {
        if (config.contains("language")) {
            return config.getString("language");
        } else {
            return "en";
        }
    }
    public int clear() {
        List<String> oldKeys = new ArrayList<>();
        int count = 0;

        ConfigurationSection keysSection = config.getConfigurationSection("keys.time");
        if (keysSection == null) return count; // Ensure the section exists to avoid NullPointerException

        for (String key : keysSection.getKeys(false)) {
            String orkey = keysSection.getString(key);
            if (orkey == null) continue; // Skip if the key value is null

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate expirationDate = LocalDate.parse(orkey, formatter);
                LocalDate now = LocalDate.now();

                if (!expirationDate.isAfter(now)) {
                    oldKeys.add(key);
                    count++;
                    config.set("keys.time." + key, null);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for key: " + key);
            }
        }

        // Clean up the corresponding entries in keys.open
        for (String oldKey : oldKeys) {
            if (config.contains("keys.open." + oldKey)) {
                config.set("keys.open." + oldKey, null);
            }
        }

        BetaKey.getPlugin(BetaKey.class).saveConfig();
        return count;
    }

}

