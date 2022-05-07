package com.github.idimabr.raphadropsell.listeners;

import com.github.idimabr.raphadropsell.RaphaDropSell;
import com.github.idimabr.raphadropsell.utils.ActionBarAPI;
import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlotListener implements Listener {

    private RaphaDropSell plugin;

    public PlotListener(RaphaDropSell plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlot(BlockGrowEvent e){
        Location location = e.getBlock().getLocation();

        if (e.getNewState().getType() != Material.CACTUS) return;

        e.getNewState().setType(Material.AIR);

        Plot plot = plugin.getPlotAPI().getPlot(location);
        if(plot == null) return;

        List<Entity> nearPlayers = location.getWorld()
                .getNearbyEntities(location, 32, 255, 32)
                .stream().filter(en -> en.getType() == EntityType.PLAYER).collect(Collectors.toList());
        if(nearPlayers.isEmpty()) return;

        double amount = plugin.getPrice();

        for (UUID owner : plot.getOwners()) {
            OfflinePlayer off = Bukkit.getOfflinePlayer(owner);

            double earnings = plugin.getEarnings().getOrDefault(off.getUniqueId(), 0.0);
            plugin.getEarnings().put(off.getUniqueId(), earnings + amount);
        }
    }
}
