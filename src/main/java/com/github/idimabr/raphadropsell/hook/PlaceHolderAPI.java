package com.github.idimabr.raphadropsell.hook;

import com.github.idimabr.raphadropsell.RaphaDropSell;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolderAPI extends PlaceholderExpansion {

    private RaphaDropSell plugin;

        public String getIdentifier() {
            return "RaphaDropSell by iDimaBR";
        }

        public String getAuthor() {
            return "iDimaBR";
        }

        public String getVersion() {
            return "1.0.0";
        }


        public String onPlaceholderRequest(Player player, String identifier) {
            if(identifier.equalsIgnoreCase("cactus")){
                if(!plugin.getEarnings().containsKey(player.getUniqueId())) return "0";

                Double earnings = plugin.getEarnings().get(player.getUniqueId());

                int cactus = (int) (earnings / plugin.getPrice());

                return String.valueOf(cactus);
            }
            if(player == null){
                return "";
            }

            if(identifier.equalsIgnoreCase("earnings")){
                if(!plugin.getEarnings().containsKey(player.getUniqueId())) return "0";
                return String.valueOf(plugin.getEarnings().get(player.getUniqueId()));
            }
            return null;
        }
    }
