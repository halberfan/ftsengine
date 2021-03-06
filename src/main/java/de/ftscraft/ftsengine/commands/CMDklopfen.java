package de.ftscraft.ftsengine.commands;

import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftsengine.main.FTSUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CMDklopfen implements CommandExecutor {

    private Engine plugin;

    public CMDklopfen(Engine plugin) {
        this.plugin = plugin;
        plugin.getCommand("klopfen").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player)cs;
            if(plugin.getAusweis(p) == null) {
                p.sendMessage("§cDu hast noch kein Ausweis. Mach dir einen mit /ausweis!");
                return true;
            }
            String message = "§c"+plugin.getAusweis(p).getFirstName() + " " + plugin.getAusweis(p).getLastName()+"§e klopft an der Tür";
            p.sendMessage(message);
            for(Entity n : p.getNearbyEntities(10, 10, 10)) {
                if(n instanceof Player)
                    n.sendMessage(message);
            }
        }
        return false;
    }
}
