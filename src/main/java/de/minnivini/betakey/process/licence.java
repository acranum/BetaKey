package de.minnivini.betakey.process;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.GUI.LicenseGUI;
import de.minnivini.betakey.Util.Luckperms;
import de.minnivini.betakey.Util.lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class licence implements CommandExecutor, TabExecutor {
    FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
    Luckperms luckperms = new Luckperms();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        String[] args;

        if (strings.length == 3 && strings[0] == "license" && strings[1] == "remove") {
            args = new String[]{strings[1], strings[1]};
        }else args = strings;
        if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            if (sender.hasPermission("betakey.removelicence")) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                if (!(p == null)) {
                    UUID uuid = p.getUniqueId();
                    if (config.contains("keys.player." + uuid)) {
                        String key = config.getString("keys.player." + uuid);
                        if (!key.equalsIgnoreCase("null")) {
                            config.set("keys.time." + key, null);
                            config.set("keys.player." + uuid, null);

                            if (luckperms.check_installed()) {
                                luckperms.removePlayerFromGroup(p.getUniqueId(), config.getString("luckperms.player_group"));
                            }
                            sender.sendMessage(lang.getMessage("removeLicense"));
                            if (p.isOnline()) {
                                Player player = p.getPlayer();
                                player.kickPlayer(lang.getMessage("removedLicenseByAdmin"));
                            }
                        } else sender.sendMessage(lang.getMessage("noLicence"));
                    } else sender.sendMessage(lang.getMessage("noLicence"));

                }
            }
            return false;
        }

        if (sender.hasPermission("betakey.licence")) {
            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();
            LicenseGUI licenseGUI = new LicenseGUI();
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
                        licenseGUI.licensegui(p,lang.getMessage("LicUntil"), bis.toString(), key, true);
                    } else {
                        p.sendMessage(lang.getMessage("noLicence2"));
                        licenseGUI.licensegui(p,lang.getMessage("noLicence2"), "", "", false);
                        Luckperms luckperms = new Luckperms();
                        luckperms.removePlayerFromGroup(p.getUniqueId(), config.getString("luckperms.player_group"));
                    }
                } else {
                    p.sendMessage(lang.getMessage("noLicence"));
                    licenseGUI.licensegui(p,lang.getMessage("noLicence"), "", "", false);
                }
            } else {
                p.sendMessage(lang.getMessage("noLicence"));
                licenseGUI.licensegui(p,lang.getMessage("noLicence"), "", "", false);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> valdArguments = new ArrayList<>();
        if (args.length > 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList("remove", "show"), valdArguments);
            return valdArguments;
        }
        return Arrays.asList("");
    }
}
