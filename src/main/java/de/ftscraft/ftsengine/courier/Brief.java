package de.ftscraft.ftsengine.courier;

import de.ftscraft.ftsengine.main.Engine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Brief
{

    public String msg;
    public String creator;
    private MapView mv;
    public long creation;
    private FTSMapRenderer renderer;
    public int id;

    private Engine plugin;

    public Brief(Engine plugin, String creator, String msg, String world)
    {
        this.msg = msg;
        this.plugin = plugin;
        this.creator = creator;
        this.creation = System.currentTimeMillis();
        this.mv = Bukkit.createMap(Bukkit.getWorld(world));
        this.id = mv.getId();
        plugin.briefe.put(id, this);
        this.renderer = new FTSMapRenderer(id, plugin);
    }

    public Brief(Engine plugin, String creator, String msg, long creation, int id)
    {
        this.plugin = plugin;
        this.creator = creator;
        this.msg = msg;
        this.creation = creation;
        this.id = id;

        this.mv = Bukkit.getMap((short) id);
        plugin.briefe.put(id, this);

        this.renderer = new FTSMapRenderer(id, plugin);
    }

    public ItemStack getMap(World w)
    {

        ItemStack map = new ItemStack(Material.MAP, 1);
        ItemMeta mapMeta = map.getItemMeta();
        mapMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS);
        mapMeta.setDisplayName("§7Brief von §e" + creator);
        map.setItemMeta(mapMeta);
        map.setDurability((short) id);

        for (MapRenderer mr : mv.getRenderers())
        {
            mv.removeRenderer(mr);
        }

        mv.setCenterZ(Integer.MAX_VALUE);
        mv.addRenderer(renderer);

        return map;
    }


    public String getMessage()
    {
        return msg;
    }

    public void loadMap(ItemStack itemMap)
    {
        MapView mv = plugin.getServer().getMap((short) id);
        if (mv.getRenderers().contains(renderer))
            return;
        for (MapRenderer mr : mv.getRenderers())
        {
            mv.removeRenderer(mr);
        }

        mv.addRenderer(renderer);
    }
}