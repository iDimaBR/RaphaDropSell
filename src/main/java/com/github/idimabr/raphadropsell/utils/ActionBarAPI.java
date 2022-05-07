package com.github.idimabr.raphadropsell.utils;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBarAPI {
  
  public static void sendActionBar(Player player, String message) {
    try {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    } catch (Throwable e) {
        e.printStackTrace();
    } 
  }
}
