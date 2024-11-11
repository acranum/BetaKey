package de.minnivini.betakey.listener;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.Luckperms;
import de.minnivini.betakey.Util.lang;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class onPlayerJoin implements Listener {
    lang lang = new lang();
    FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (BetaKey.getPlugin(BetaKey.class).betaCheack()) {
            Player p = e.getPlayer();
            UUID uuid = p.getUniqueId();
            if (p.isOp()) {
                return;
            }
            if (!config.contains("keys.player." + uuid)) {
                config.set("keys.player." + uuid, "null");
                BetaKey.getPlugin(BetaKey.class).saveConfig();
            }
            String key;
            if (config.getString("keys.player." + uuid) != "null") {
                key = config.getString("keys.player." + uuid);
            } else {
                key = "null";
            }
            if (config.getString("keys.time." + key) != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate bis = LocalDate.parse(Objects.requireNonNull(config.getString("keys.time." + key)), formatter);
                LocalDate now = LocalDate.now();
                if (bis.isAfter(now)) {
                    //nothing (normal Playing)
                } else {
                    removePlayerEtTime(p, key);
                    sendToSpawn(p);
                }
            } else {
                sendToSpawn(p);
                Bukkit.getScheduler().runTaskLater(BetaKey.getPlugin(BetaKey.class), () -> {
                    p.sendMessage(lang.getMessage("noLicence"));
                }, 20);

            }
        }
    }
    public void removePlayerEtTime(Player p, String key) {
        UUID uuid = p.getUniqueId();
        FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
        if (config.contains("keys.player." + uuid)) {
            config.set("keys.player." + uuid, null);
            p.sendMessage(lang.getMessage("KeyExpired"));
            Luckperms luckperms = new Luckperms();
            if (luckperms.check_installed()) {
                luckperms.removePlayerFromGroup(p.getUniqueId(), config.getString("luckperms.player_group"));
            }

        }
        if (config.contains("keys.time." + key)) {
            config.set("keys.time." + key, null);
        }
        BetaKey.getPlugin(BetaKey.class).saveConfig();
    }
    public void sendToSpawn(Player p) {
        Location spawn = null;
        String worldName = config.getString("BetaSpawn.world");
        World world = Bukkit.getWorld(worldName);
        int x = config.getInt("BetaSpawn.x");
        int y = config.getInt("BetaSpawn.y");
        int z = config.getInt("BetaSpawn.z");
        float yaw = (float) config.getDouble("BetaSpawn.yaw");
        float pitch = (float) config.getDouble("BetaSpawn.pitch");

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
