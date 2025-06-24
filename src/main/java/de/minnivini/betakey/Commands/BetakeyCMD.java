package de.minnivini.betakey.Commands;

import de.minnivini.betakey.BetaKey;
import de.minnivini.betakey.Util.*;
import de.minnivini.betakey.process.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BetakeyCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        register register = new register();
        generate generate = new generate();
        spawn setSpawn = new spawn();
        licence licence = new licence();
        clear clear = new clear();
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("register")) {
                if (commandSender instanceof Player) {
                    if (args.length > 1 && args[1] != null) {
                        String code = args[1];
                        register.register(code, ((Player) commandSender).getPlayer(), false);
                    } else {
                        commandSender.sendMessage(lang.getMessage("enterKey"));
                    }
                }
            } else if (args[0].equalsIgnoreCase("generate")) {
                if (args.length > 1 && validNBumber(args[1])) {
                    if (args[1].equals("0")) {
                        if (args.length > 2 && validNBumber(args[2])) {
                            generate.generate(Integer.parseInt(args[1]), commandSender, Integer.parseInt(args[2]));
                        }
                    } else {
                        if (args.length > 2 && validNBumber(args[2])) {
                            generate.generate(Integer.parseInt(args[1]), commandSender, Integer.parseInt(args[2]));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("setSpawn")) {
                setSpawn.setSpawn(commandSender);
            } else if (args[0].equalsIgnoreCase("setLobby")) {
                setSpawn.setLobby(commandSender);
            } else if (args[0].equalsIgnoreCase("spawn")) {
                if (args.length > 1) {
                    setSpawn.spawn(commandSender, args[1]);
                } else setSpawn.spawn(commandSender, null);

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

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;

        final List<String> valdArguments = new ArrayList<>();
        List<String> arguments = new ArrayList<>();

        if (p.hasPermission("license")) arguments.add("license");
        if (p.hasPermission("setSpawn")) arguments.add("setSpawn");
        if (p.hasPermission("setLobby")) arguments.add("setLobby");
        if (p.hasPermission("spawn")) arguments.add("spawn");
        if (p.hasPermission("reload")) arguments.add("reload");
        if (p.hasPermission("clear")) arguments.add("clear");
        if (p.hasPermission("generate")) arguments.add("generate");
        if (p.hasPermission("register")) arguments.add("register");

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], arguments, valdArguments);
            return valdArguments;
        }

        return Arrays.asList("");
    }

    private boolean validNBumber(String num) {
        num = num.replaceAll("[^0-9 ]", "x");
        if (num == null || num.contains("x") || num.equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }
}
