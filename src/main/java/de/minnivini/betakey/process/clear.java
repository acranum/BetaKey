package de.minnivini.betakey.process;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.lang;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class clear {
    public void  clear(CommandSender sender) {
        List<String> oldKeys = new ArrayList<>();
        int count = 0;
        FileConfiguration config = BetaKey.getPlugin(BetaKey.class).config;


        ConfigurationSection keysSection = config.getConfigurationSection("keys.time");
        if (keysSection == null) return;

        for (String key : keysSection.getKeys(false)) {
            String time = keysSection.getString(key);
            if (time == null) continue; // Skip if the key value is null

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate expirationDate = LocalDate.parse(time, formatter);
                System.out.println(expirationDate);
                LocalDate now = LocalDate.now();
                System.out.println(now);

                if (expirationDate.isBefore(now)) {
                    oldKeys.add(key);
                    count++;
                    config.set("keys.time." + key, null);
                }
            } catch (DateTimeParseException e) {
                if (time == "") {
                    config.set("keys.time." + key, null);
                } else System.out.println("Invalid date format for key: " + key);
            }
        }

        // Clean up the corresponding entries in keys.open
        for (String oldKey : oldKeys) {
            if (config.contains("keys.open." + oldKey)) {
                config.set("keys.open." + oldKey, null);
            }
        }

        BetaKey.getPlugin(BetaKey.class).saveConfig();


        String message = lang.getMessage("cleared");
        message = message.replace("{count}", String.valueOf(count));
        sender.sendMessage(message);
    }

    private void checkTimeKeys() { //removes keys that are expired

    }
    private void checkOpenKeys() { //removes keys that have a time but aren't

    }
}
