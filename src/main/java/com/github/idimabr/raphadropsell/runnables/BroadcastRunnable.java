package com.github.idimabr.raphadropsell.runnables;

import com.github.idimabr.raphadropsell.RaphaDropSell;
import com.github.idimabr.raphadropsell.utils.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BroadcastRunnable extends BukkitRunnable {

    private RaphaDropSell plugin;

    public BroadcastRunnable(RaphaDropSell plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        HashMap<UUID, Double> copyEarnings = new HashMap<>(plugin.getEarnings());

        for (Map.Entry<UUID, Double> entry : copyEarnings.entrySet()) {
            OfflinePlayer owner = Bukkit.getOfflinePlayer(entry.getKey());

            double earnings = entry.getValue();

            String earningsFormated = earnings > 1000 ? (Math.round(earnings / 100) / 10.0) + "K" : earnings + "c";

            int cactus = (int) (earnings / plugin.getPrice());

            plugin.getVault().depositPlayer(owner, earnings);
            if(!owner.isOnline()) continue;

            Player player = owner.getPlayer();

            if(!plugin.getPlayersDisabledMessage().contains(owner.getUniqueId()))
                ActionBarAPI.sendActionBar(player,
                        plugin.getConfig().getString("ActionBar").replace("&","§")
                                .replace("%earns%", earningsFormated)
                                .replace("%cactus%", cactus+"")
                                .replace("%player%", owner.getName())
                );


            plugin.getEarnings().remove(owner.getUniqueId());
        }
    }
}
