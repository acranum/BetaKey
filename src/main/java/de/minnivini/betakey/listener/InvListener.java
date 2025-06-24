package de.minnivini.betakey.listener;

import de.minnivini.betakey.process.register;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvListener implements Listener {
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        register register = new register();

        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() != null) {
            if (e.getView().getTitle().equals("Betakey License")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().hasLocalizedName()) {
                    switch (e.getCurrentItem().getItemMeta().getLocalizedName()) {
                        case "key":
                            String key = e.getCurrentItem().getItemMeta().getLore().toString();
                            break;
                    }

                }
            } else if (e.getView().getTitle().equals("Inventory")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().hasLocalizedName()) {
                    switch (e.getCurrentItem().getItemMeta().getLocalizedName()) {
                        case "keyItem":
                            register.register(e.getCurrentItem().getItemMeta().getLore().toString(), p, true);
                            break;
                    }

                }
            }
        }
    }
}
