package com.github.idimabr.raphadropsell;

import com.github.idimabr.raphadropsell.commands.FarmCommand;
import com.github.idimabr.raphadropsell.hook.PlaceHolderAPI;
import com.github.idimabr.raphadropsell.listeners.PlotListener;
import com.github.idimabr.raphadropsell.runnables.BroadcastRunnable;
import com.github.idimabr.raphadropsell.utils.ConfigUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellectualcrafters.plot.api.PlotAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class RaphaDropSell extends JavaPlugin {

    private static RaphaDropSell instance;
    private static PlotAPI api;
    public static Economy economy = null;
    private static ConfigUtil config = null;

    private HashMap<UUID, Double> EARNINGS = Maps.newHashMap();
    private List<UUID> disabledMessage = Lists.newArrayList();
    private int price;

    public static RaphaDropSell getPlugin(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        config = new ConfigUtil(null, "config.yml", false);
        if (!setupEconomy() ) {
            getLogger().info("O Vault nao pode ser encontrado, desabilitando o plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            getLogger().info("Placeholders registrados com sucesso!");
            new PlaceHolderAPI().register();
        }else{
            getLogger().info("O PlaceHolderAPI nao foi encontrado, placeholders nao registrados.");
        }

        api = new PlotAPI();
        price = config.getInt("PriceOfCactus");

        getCommand("farmchat").setExecutor(new FarmCommand(this));
        Bukkit.getPluginManager().registerEvents(new PlotListener(this), this);

        new BroadcastRunnable(this)
                .runTaskTimerAsynchronously(this,
                        20L * config.getInt("sendEarningsTimeSeconds"),
                        20L * config.getInt("sendEarningsTimeSeconds")
                );

        config.saveConfig();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        config.reloadConfig();
    }

    public PlotAPI getPlotAPI() {
        return api;
    }

    public Economy getVault() {
        return economy;
    }

    public HashMap<UUID, Double> getEarnings() {
        return EARNINGS;
    }

    public List<UUID> getPlayersDisabledMessage() {
        return disabledMessage;
    }

    public int getPrice() {
        return price;
    }
}
