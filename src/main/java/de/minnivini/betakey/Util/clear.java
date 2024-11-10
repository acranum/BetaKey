package de.minnivini.betakey.Util;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.lang;
import org.bukkit.command.CommandSender;

public class clear {
    public void  clear(CommandSender sender) {
        lang lang = new lang();
        int count = BetaKey.getPlugin(BetaKey.class).clear();
        String message = lang.getMessage("cleared");
        message = message.replace("{count}", String.valueOf(count));
        sender.sendMessage(message);
    }
}
