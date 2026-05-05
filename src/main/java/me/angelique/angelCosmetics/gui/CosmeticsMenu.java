package me.angelique.angelCosmetics.gui;

import me.angelique.angelCosmetics.AngelCosmetics;
import me.angelique.angelCosmetics.model.PlayerCosmeticState;
import me.angelique.angelCosmetics.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class CosmeticsMenu {

    public static final String TITLE = "§8Angel Cosmetics";

    private CosmeticsMenu() {
    }

    public static void open(AngelCosmetics plugin, Player player) {
        PlayerCosmeticState state = plugin.getCosmeticManager().getState(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 27, TITLE);

        inventory.setItem(11, new ItemBuilder(Material.BLAZE_POWDER)
                .name("&6Trails")
                .lore("&7Current: &f" + state.getTrailType().getDisplayName(), "&eClick to cycle trail")
                .build());

        inventory.setItem(13, new ItemBuilder(Material.ELYTRA)
                .name("&bWings")
                .lore("&7Current: &f" + state.getWingType().getDisplayName(), "&eClick to cycle wings")
                .build());

        inventory.setItem(15, new ItemBuilder(Material.ENDER_PEARL)
                .name("&dRecall")
                .lore("&7Current: &f" + state.getRecallType().getDisplayName(), "&eClick to cycle recall")
                .build());

        inventory.setItem(22, new ItemBuilder(Material.NETHER_STAR)
                .name("&aPreview Recall")
                .lore("&7Play the selected recall effect.")
                .build());

        player.openInventory(inventory);
    }
}
