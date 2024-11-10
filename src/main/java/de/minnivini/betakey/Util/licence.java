package de.minnivini.betakey.Util;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class licence implements CommandExecutor {
    lang lang = new lang();
    FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender.hasPermission("betakey.licence")) {
            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();

            if (config.contains("keys.player." + uuid)) {
                String nul = config.getString("keys.player." + uuid);
                if (!nul.equalsIgnoreCase("null")) {
                    String key = config.getString("keys.player." + uuid);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate bis = LocalDate.parse(Objects.requireNonNull(config.getString("keys.time." + key)), formatter);
                    LocalDate now = LocalDate.now();

                    if (bis.isAfter(now)) {
                        bis.format(formatter);
                        //p.sendMessage(lang.getMessage("licUntil"));
                        String message = lang.getMessage("LicUntil");
                        message = message.replace("{until}", bis.toString());
                        message = message.replace("{key}", key);
                        p.sendMessage(message);
                    } else p.sendMessage(lang.getMessage("noLicence2"));
                } else p.sendMessage(lang.getMessage("noLicence"));
            } else {
                p.sendMessage(lang.getMessage("noLicence"));
            }
        }
        return false;
    }
}
