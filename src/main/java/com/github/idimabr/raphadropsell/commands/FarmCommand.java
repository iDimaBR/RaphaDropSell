package com.github.idimabr.raphadropsell.commands;

import com.github.idimabr.raphadropsell.RaphaDropSell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FarmCommand implements CommandExecutor {

    private RaphaDropSell plugin;

    public FarmCommand(RaphaDropSell plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas jogadores");
            return false;
        }

        Player player = (Player) sender;
            if(plugin.getPlayersDisabledMessage().contains(player.getUniqueId())){
                plugin.getPlayersDisabledMessage().remove(player.getUniqueId());
                player.sendMessage(plugin.getConfig().getString("FarmChat.Enable").replace("&","§"));
                return true;
            }else{
                plugin.getPlayersDisabledMessage().add(player.getUniqueId());
                player.sendMessage(plugin.getConfig().getString("FarmChat.Disable").replace("&","§"));
                return true;
            }
    }
}
