package de.minnivini.betakey.process;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class spawn {
    lang lang = new lang();
    FileConfiguration config = BetaKey.getPlugin(BetaKey.class).getConfig();
    public void setSpawn(CommandSender sender) {
        Player p = (Player) sender;
        Location location = p.getLocation();
        if (p.isOp()) {
            config.set("BetaSpawn.world", location.getWorld().getName());
            config.set("BetaSpawn.x", location.getX());
            config.set("BetaSpawn.y", location.getY());
            config.set("BetaSpawn.z", location.getZ());
            config.set("BetaSpawn.yaw", location.getYaw());
            config.set("BetaSpawn.pitch", location.getPitch());

            BetaKey.getPlugin(BetaKey.class).saveConfig();
            p.sendMessage(lang.getMessage("SpawnSet"));
        } else p.sendMessage(lang.getMessage("NoPerm"));
    }
    public void setLobby(CommandSender sender) {
        Player p = (Player) sender;
        Location location = p.getLocation();
        if (p.isOp()) {
            config.set("LobbySpawn.world", location.getWorld().getName());
            config.set("LobbySpawn.x", location.getX());
            config.set("LobbySpawn.y", location.getY());
            config.set("LobbySpawn.z", location.getZ());
            config.set("LobbySpawn.yaw", location.getYaw());
            config.set("LobbySpawn.pitch", location.getPitch());

            BetaKey.getPlugin(BetaKey.class).saveConfig();
            p.sendMessage(lang.getMessage("SpawnSet"));
        } else p.sendMessage(lang.getMessage("NoPerm"));
    }
    public void spawn(CommandSender sender) {
        if (sender instanceof Player && sender.hasPermission("betakey.spawn")) {
            Player p = (Player) sender;
            Location spawn = null;

            String worldName = config.getString("BetaSpawn.world");
            World world = Bukkit.getWorld(worldName);
            int x = config.getInt("BetaSpawn.x");
            int y = config.getInt("BetaSpawn.y");
            int z = config.getInt("BetaSpawn.z");
            float yaw = (float) config.getDouble("BetaSpawn.yaw");
            float pitch = (float) config.getDouble("BetaSpawn.pitch");

            try {
                spawn = new Location(world, x, y, z, yaw , pitch);
                p.teleport(spawn);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}