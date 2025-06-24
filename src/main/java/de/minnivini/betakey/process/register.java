package de.minnivini.betakey.process;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.Luckperms;
import de.minnivini.betakey.Util.lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class register {
    FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
    public void register(String key, Player p, boolean item) {
        if (p.hasPermission("betakey.register")) {
            String check = config.getString("keys.open." + key);
            if (check != null) {
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
                            p.sendMessage(lang.getMessage("alreadyLicenced"));
                            return;
                        }
                    }
                }
                if (bis.isAfter(now)) {
                    if (config.getBoolean("beta")) {
                        p.sendMessage(lang.getMessage("registered").replace("{date}", date));
                        config.set("keys.open." + key, null);
                        config.set("keys.player." + uuid, key);
                        BetaKey.getPlugin(BetaKey.class).saveConfig();
                        sendToLobby(p);
                        Luckperms luckperms = new Luckperms();
                        luckperms.addPlayerToGroup(p, config.getString("luckperms.player_group"));
                        if (item) p.getInventory().removeItem(p.getInventory().getItemInMainHand());

                    } else p.sendMessage(lang.getMessage("betaAlreadyfinished"));
                } else p.sendMessage(lang.getMessage("keyNotGood"));
            } else p.sendMessage(lang.getMessage("wrongKey"));
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
            if (!p.isOp() || !p.hasPermission("betakey.spawn")) {
                p.kickPlayer(lang.getMessage("spawnpoint"));
            }
        }
    }
}
