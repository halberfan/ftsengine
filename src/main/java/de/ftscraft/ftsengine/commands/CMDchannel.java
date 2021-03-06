package de.ftscraft.ftsengine.commands;

import de.ftscraft.ftsengine.main.Engine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CMDchannel implements CommandExecutor, TabCompleter
{

    private Engine plugin;

    public CMDchannel(Engine plugin)
    {
        this.plugin = plugin;
        plugin.getCommand("channel").setExecutor(this);
        plugin.getCommand("channel").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args)
    {
        if(!(cs instanceof Player)) {
            cs.sendMessage(plugin.msgs.ONLY_PLAYER);
            return true;
        }

        if(args.length == 1) {

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String label, String[] args)
    {
        final List<String> com = new ArrayList<>(Arrays.asList("roleplay", "handel", "flüstern", "rufen"));
        //StringUtil.copyPartialMatches(args[0], com, com);
        Collections.sort(com);
        return com;
    }
}
