package de.ftscraft.ftsengine.listener;

import de.ftscraft.ftsengine.brett.Brett;
import de.ftscraft.ftsengine.courier.Briefkasten;
import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftsengine.main.FTSUser;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.logging.Level;

public class SignWriteListener implements Listener {

    private Engine plugin;

    public SignWriteListener(Engine plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWirte(SignChangeEvent event) {

        //If player wants to create a briefkasten
        if(event.getLine(0).equalsIgnoreCase("[Briefkasten]")) {

            Player p = event.getPlayer();
            FTSUser user = plugin.getPlayer().get(p);

            Location loc = event.getBlock().getLocation();

            Block block = event.getBlock();

            if (block != null && block.getState() instanceof Sign)
            {
                BlockData data = block.getBlockData();
                if (data instanceof Directional)
                {
                    Directional directional = (Directional)data;
                    Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());

                    if(blockBehind.getType() == Material.CHEST) {

                        if(plugin.briefkasten.containsKey(p.getUniqueId())) {

                            Briefkasten briefkasten = plugin.briefkasten.get(p.getUniqueId());

                            p.sendMessage("§cDu hast bereits einen Briefkasten, zerstöre diesen, bevor du einen neuen erstellst");
                            p.sendMessage("§cFalls du vergessen hast wo er ist, hier die Koordinaten:");
                            p.sendMessage("§cX: "+briefkasten.getLocation().getX() + " Y: " +briefkasten.getLocation().getY() + " Z: " + briefkasten.getLocation().getZ());

                            event.setCancelled(true);

                            return;

                        }

                        Briefkasten briefkasten = new Briefkasten(plugin, blockBehind.getLocation(), p.getUniqueId());


                        event.setLine(0, "§7[§2Briefkasten§7]");
                        event.setLine(1, p.getName());

                        p.sendMessage("§cDu hast deinen Briefkasten erfolgreich erstellt!");

                    }

                }
            } else p.sendMessage("§cHallo");


        }

        //Schwarzes Brett
        if (event.getLine(0).equalsIgnoreCase("Schwarzes Brett"))
            if (event.getLine(1).length() > 3) {
                String name = event.getLine(1);
                if (event.getLine(2).equalsIgnoreCase(""))
                    if (event.getLine(3).equalsIgnoreCase("")) {

                        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getBlock().getState();
                        event.setLine(0, "§4Schwarzes Brett");
                        for (Brett all : plugin.bretter.values()) {
                            if (all.getName().equals(name)) {
                                event.getPlayer().sendMessage("§cDieser Name ist schon vorhanden. Probier ein anderen");
                                return;
                            }
                        }
                        plugin.bretter.put(event.getBlock().getLocation(), new Brett(sign, event.getBlock().getLocation(), event.getPlayer().getUniqueId(), name, plugin));
                        event.getPlayer().sendMessage("§7[§bSchwarzes Brett§7] Du hast das Schwarze Brett erfolgreich erstellt");

                    }
            } else
                event.getPlayer().sendMessage("§7[§bSchwarzes Brett§7] Der Name (2. Zeile) muss mind. 4 Zeichen haben!");
        if (!(event.getPlayer().hasPermission("blackboard.create"))) {
            if (event.getLine(0).equalsIgnoreCase("&4Schwarzes Brett")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§7[§bSchwarzes Brett§7] Mach sowas nicht! Das könnte Fehler verursachen!");
            }
        }
    }

}
