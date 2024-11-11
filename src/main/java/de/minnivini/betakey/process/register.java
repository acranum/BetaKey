package de.minnivini.betakey.process;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.Luckperms;
import de.minnivini.betakey.Util.lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class register {
    FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
    de.minnivini.betakey.Util.lang lang = new lang();
    public void register(String key, CommandSender commandSender) {
        if (commandSender.hasPermission("betakey.register")) {
            String check = config.getString("keys.open." + key);
            if (check != null) {
                Player p = (Player) commandSender;
                UUID uuid = p.getUniqueId();
                String date = getDate(key);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate bis = LocalDate.parse(Objects.requireNonNull(config.getString("keys.time." + key)), formatter);
                LocalDate now = LocalDate.now();

                String curentkey = config.getString("keys.player." + uuid);
                if (config.getString("keys.time." + curentkey) != null) {
                    LocalDate currentKeydate = LocalDate.parse(Objects.requireNonNull(config.getString("keys.time." + curentkey)), formatter);

                    if (currentKeydate != null && currentKeydate.isAfter(bis) || currentKeydate.isEqual(bis)) {
                        if (!p.isOp()) {
                            p.sendMessage("§4Du hast bereits eine längere Betakey license! ");
                            return;
                        }
                    }
                }


                if (bis.isAfter(now)) {
                    if (config.getBoolean("beta")) {
                        p.sendMessage("§a>> Der Beta Key ist richtig. Du wurdest erfolgreich registriert (gültig bis zum: " + date + ")");
                        config.set("keys.open." + key, null);
                        config.set("keys.player." + uuid, key);
                        BetaKey.getPlugin(BetaKey.class).saveConfig();
                        sendToLobby(p);
                        Luckperms luckperms = new Luckperms();
                        luckperms.addPlayerToGroup(p.getUniqueId(), config.getString("luckperms.player_group"));
                    } else {
                        p.sendMessage("§4>> Die Betaphase ist bereits beendet!");
                    }

                } else {
                    p.sendMessage("§4>> Der Beta Key ist abgelaufen oder nicht mehr gültig");
                }

            } else {
                commandSender.sendMessage("§4Der Beta Key ist falsch");
            }
        }
    }
    public String getDate(String key) {
        FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
        if (config.getString("keys.time." + key) != null) {
            return config.getString("keys.time." + key);
        } else return "Error!";
    }

    private void sendToLobby(Player p) {
        Location spawn = null;
        String worldName = config.getString("LobbySpawn.world");
        World world = Bukkit.getWorld(worldName);
        int x = config.getInt("LobbySpawn.x");
        int y = config.getInt("LobbySpawn.y");
        int z = config.getInt("LobbySpawn.z");
        float yaw = (float) config.getDouble("LobbySpawn.yaw");
        float pitch = (float) config.getDouble("LobbySpawn.pitch");

        try {
            spawn = new Location(world, x, y, z, yaw, pitch);
            p.teleport(spawn);
            Bukkit.getScheduler().runTaskLater(BetaKey.getPlugin(BetaKey.class), () -> {
                Location spawn1 = new Location(world, x, y, z, yaw, pitch);
                p.teleport(spawn1);
            }, 20);
        } catch (Exception ey) {
            if (!p.isOp()) {
                p.kickPlayer(lang.getMessage("spawnpoint"));
            }
        }
    }
}
