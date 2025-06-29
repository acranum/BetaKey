package de.minnivini.betakey.Util;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Luckperms {
    LuckPerms luckPerms = LuckPermsProvider.get();

    public void CreateGroup(String groupName) {
        if (check_installed()) {
            Group group = getGroup(groupName);
            if (!checkGroup(groupName)) {
            group = luckPerms.getGroupManager().createAndLoadGroup(groupName).join();
            //group.data().add(Node.builder("example.permission").build());
            saveGroup(group);
            }
        }
    }

    public boolean checkGroup(String groupName) {
        Group group = luckPerms.getGroupManager().getGroup(groupName);
        return group != null;
    }

    public void saveGroup(Group group) {
        if (check_installed()) {
            luckPerms.getGroupManager().saveGroup(group);
        }
    }

    public Group getGroup(String name) {
        if (check_installed()) {
            return luckPerms.getGroupManager().getGroup(name);
        }
        return null;
    }

    public void addPlayerToGroup(Player p, String groupName) {
        UUID uuid = p.getUniqueId();
        if (check_installed()) {
            // Gruppe abrufen
            Group group = getGroup(groupName);
            if (group == null) {
                p.sendMessage(lang.getMessage("NoGroup").replace("name", groupName));
                return;
            }
            // Spieler abrufen (lädt den Spieler, falls er nicht im Cache ist)
            User user = luckPerms.getUserManager().loadUser(uuid).join();
            if (user == null) {
                p.sendMessage(lang.getMessage("PlayerNotFound").replace("{name}", p.getName()));
                System.out.println(lang.getMessage("PlayerNotFound").replace("{name}", p.getName()));
                return;
            }
            // InheritanceNode für die Gruppe erstellen
            InheritanceNode groupNode = InheritanceNode.builder(groupName).build();
            // Prüfen, ob der Spieler bereits in der Gruppe ist
            user.data().add(groupNode);
            luckPerms.getUserManager().saveUser(user);
        }
    }
    public void removePlayerFromGroup(UUID playerUUID, String groupName) {
        if (check_installed()) {
            LuckPerms luckPerms = LuckPermsProvider.get();

            User user = luckPerms.getUserManager().loadUser(playerUUID).join();

            InheritanceNode groupNode = InheritanceNode.builder(groupName).build();

            user.data().remove(groupNode);

            // Änderungen speichern
            luckPerms.getUserManager().saveUser(user);
        }
    }
    public boolean check_installed() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("LuckPerms");
        return plugin != null && plugin.isEnabled();
    }
}
