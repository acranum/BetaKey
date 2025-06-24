package de.minnivini.betakey;

import de.minnivini.betakey.Commands.BetakeyCMD;
import de.minnivini.betakey.Util.Luckperms;
import de.minnivini.betakey.listener.InvListener;
import de.minnivini.betakey.listener.PlayerInteract;
import de.minnivini.betakey.process.licence;
import de.minnivini.betakey.Util.lang;
import de.minnivini.betakey.listener.onPlayerJoin;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.chat.TextComponent;


public final class BetaKey extends JavaPlugin {
    public FileConfiguration config = getConfig();
    public LuckPerms api;

    @Override
    public void onEnable() {
        Luckperms luckperms = new Luckperms();
        saveDefaultConfig();
        lang.createLanguageFolder();
        lang.checkLanguageUpdates();

        if (!betaCheack()) {
            getLogger().info(lang.getMessage("BetaDis"));
        }
        getCommand("betakey").setExecutor(new BetakeyCMD());
        getCommand("license").setExecutor(new licence());
        //getCommand("register").setExecutor(new register());


        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new InvListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);

        if (luckperms.check_installed()) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                api = provider.getProvider();
            }
            luckperms.CreateGroup("BetaKey_default");
        }
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
    }

    public boolean betaCheack() {
        Boolean beta = config.getBoolean("beta");
        if (beta.equals(false)) {
            return false;
        }
        return true;
    }

    public void reaload() {
        saveDefaultConfig();
        reloadConfig();
    }
    public String getLanguage() {
        if (config.contains("language")) {
            return config.getString("language");
        } else {
            return "en";
        }
    }
    public static TextComponent copyMSG(String msg) {
        TextComponent textComponent = new TextComponent(msg);
        textComponent.setColor(ChatColor.LIGHT_PURPLE);
        textComponent.setUnderlined(true);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, msg));
        return textComponent;
    }
}

