package de.minnivini.betakey.listener;

import de.minnivini.betakey.process.register;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = e.getPlayer();
        register register = new register();

        if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
            ItemMeta itemMeta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
            if (itemMeta.getLore() == null) return;
            if (itemMeta.getLore().toString().equals("[This is a betakey license. Redeem it by right clicking]")) {
                e.setCancelled(true);
                register.register(itemMeta.getDisplayName(), p, true);
            }
        }
    }

}
