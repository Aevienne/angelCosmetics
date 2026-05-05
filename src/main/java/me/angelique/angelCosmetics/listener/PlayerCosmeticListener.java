package me.angelique.angelCosmetics.listener;

import me.angelique.angelCosmetics.AngelCosmetics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerCosmeticListener implements Listener {

    private final AngelCosmetics plugin;

    public PlayerCosmeticListener(AngelCosmetics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getCosmeticManager().getState(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getCosmeticManager().cancelRecall(event.getPlayer().getUniqueId());
    }
}
