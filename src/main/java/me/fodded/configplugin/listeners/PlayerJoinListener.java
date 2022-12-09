package me.fodded.configplugin.listeners;

import me.fodded.configplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        String joinMessage = Main.getConfigHandler().getOn_join_message().replace("%player%", player.getName());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
    }
}
