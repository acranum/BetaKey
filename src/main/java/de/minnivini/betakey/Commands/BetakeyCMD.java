package de.minnivini.betakey.Commands;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BetakeyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        register register = new register();
        generate generate = new generate();
        spawn setSpawn = new spawn();
        licence licence = new licence();
        clear clear = new clear();
        lang lang = new lang();
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("register")) {
                if (commandSender instanceof Player) {
                    if (args[1] != null) {
                        String code = args[1];
                        register.register(code, commandSender);
                    } else {
                        commandSender.sendMessage(lang.getMessage("enterKey"));
                    }
                }
            } else if (args[0].equalsIgnoreCase("generate")) {
                if (args.length > 1) {
                    if (args[1].equals("0")) {
                        if (args.length > 2) {
                            generate.generate(Integer.parseInt(args[1]), commandSender, Integer.parseInt(args[2]));
                        }
                    } else {
                        if (args.length > 2) {
                            generate.generate(Integer.parseInt(args[1]), commandSender, Integer.parseInt(args[2]));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("setSpawn")) {
                setSpawn.setSpawn(commandSender);
            } else if (args[0].equalsIgnoreCase("setLobby")) {
                setSpawn.setLobby(commandSender);
            } else if (args[0].equalsIgnoreCase("spawn")) {
                setSpawn.spawn(commandSender);
            } else if (args[0].equalsIgnoreCase("reload")) {
                BetaKey.getPlugin(BetaKey.class).reaload();
                commandSender.sendMessage("§aRealoaded config!");
            } else if (args[0].equalsIgnoreCase("license")) {
                if (commandSender instanceof Player) {
                    licence.onCommand(commandSender, command, s, args);
                }
            } else if (args[0].equalsIgnoreCase("clear")) {
                clear.clear(commandSender);
            }
        }
        return false;
    }
}
