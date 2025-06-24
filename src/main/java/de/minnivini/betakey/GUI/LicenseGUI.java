package de.minnivini.betakey.GUI;

import de.minnivini.betakey.Util.ItemBuilder;
import de.minnivini.betakey.Util.lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;

public class LicenseGUI {

    public void licensegui(Player p, String msg, String until, String key, boolean success){

        Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER,  "Betakey License");

        ItemStack blackglass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).build();
        ItemStack redglass = new ItemBuilder(Material.BARRIER).setDisplayname(msg).build();

        inv.setItem(0, blackglass);
        inv.setItem(1, blackglass);
        inv.setItem(2, blackglass);
        inv.setItem(3, blackglass);

        inv.setItem(4, redglass);

        inv.setItem(5, blackglass);
        inv.setItem(6, blackglass);
        inv.setItem(7, blackglass);
        inv.setItem(8, blackglass);

       if (success) {
           inv.setItem(4, new ItemBuilder(Material.NAME_TAG).setDisplayname(lang.getMessage("LicUntil").replace("{until}", until).replace("{key}", key)).setLore(ChatColor.GRAY + key).setLocalizedName("key").setGlow().build());
       }

       p.openInventory(inv);
    }
}
