package me.angelique.angelCosmetics.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color(name));
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder lore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> lines = Arrays.stream(lore).map(ItemBuilder::color).collect(Collectors.toList());
            meta.setLore(lines);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
