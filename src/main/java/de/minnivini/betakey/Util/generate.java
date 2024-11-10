package de.minnivini.betakey.Util;

import de.minnivini.betakey.BetaKey;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class generate {

    public void generate(int num, CommandSender sender, int bis){
        if (sender.hasPermission("betakey.generate")) {
            for (int i = 0; i < num; i++) {
                String key = generateKey();
                Bukkit.getLogger().info(key);
                addKeytoConfig(key);
                ConfigBis(bis, key);
                if (sender instanceof Player) {
                    sender.sendMessage(key);
                }
            }
        }
    }
    public void ConfigBis(int bis, String key) {
        FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
        LocalDate now = LocalDate.now();
        LocalDate date = now.plusDays(bis);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        config.set("keys.time." + key, date.format(dtf));
        BetaKey.getPlugin(BetaKey.class).saveConfig();
    }

    public String generateKey() {
        Random random = new Random();
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            // Füge einen Trennstrich nach jedem dritten Zeichen hinzu
            if (i > 0 && i % 4 == 0) {
                keyBuilder.append("-");
            }

            // Füge zufällige Buchstaben und Zahlen hinzu
            if (random.nextBoolean()) {
                // Generiere eine Ziffer
                keyBuilder.append(random.nextInt(10));
            } else {
                // Generiere einen Großbuchstaben
                keyBuilder.append((char) (random.nextInt(26) + 'A'));
            }
        }

        return keyBuilder.toString();
    }
    private void addKeytoConfig(String key) {
        FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
        config.set("keys.open." + key, "");
        BetaKey.getPlugin(BetaKey.class).saveConfig();
    }
}
