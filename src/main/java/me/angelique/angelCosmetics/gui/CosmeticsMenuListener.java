package me.angelique.angelCosmetics.gui;

import me.angelique.angelCosmetics.AngelCosmetics;
import me.angelique.angelCosmetics.model.PlayerCosmeticState;
import me.angelique.angelCosmetics.model.RecallType;
import me.angelique.angelCosmetics.model.TrailType;
import me.angelique.angelCosmetics.model.WingType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class CosmeticsMenuListener implements Listener {

    private final AngelCosmetics plugin;

    public CosmeticsMenuListener(AngelCosmetics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle() == null || !event.getView().getTitle().equals(CosmeticsMenu.TITLE)) {
            return;
        }

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        PlayerCosmeticState state = plugin.getCosmeticManager().getState(player.getUniqueId());
        int slot = event.getRawSlot();

        if (slot == 11) {
            TrailType[] values = TrailType.values();
            state.setTrailType(values[(state.getTrailType().ordinal() + 1) % values.length]);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
            CosmeticsMenu.open(plugin, player);
            return;
        }

        if (slot == 13) {
            WingType[] values = WingType.values();
            state.setWingType(values[(state.getWingType().ordinal() + 1) % values.length]);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
            CosmeticsMenu.open(plugin, player);
            return;
        }

        if (slot == 15) {
            RecallType[] values = RecallType.values();
            state.setRecallType(values[(state.getRecallType().ordinal() + 1) % values.length]);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 0.8f);
            CosmeticsMenu.open(plugin, player);
            return;
        }

        if (slot == 22) {
            plugin.getCosmeticManager().playRecall(player, null);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1.0f, 1.0f);
        }
    }
}
