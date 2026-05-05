package me.angelique.angelCosmetics;

import me.angelique.angelCosmetics.command.CosmeticsCommand;
import me.angelique.angelCosmetics.command.CosmeticsTeleportCommand;
import me.angelique.angelCosmetics.command.RecallCommand;
import me.angelique.angelCosmetics.cosmetic.CosmeticManager;
import me.angelique.angelCosmetics.gui.CosmeticsMenuListener;
import me.angelique.angelCosmetics.listener.PlayerCosmeticListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AngelCosmetics extends JavaPlugin {

    private CosmeticManager cosmeticManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.cosmeticManager = new CosmeticManager(this);
        this.cosmeticManager.start();

        getServer().getPluginManager().registerEvents(new CosmeticsMenuListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCosmeticListener(this), this);

        if (getCommand("cosmetics") != null) {
            getCommand("cosmetics").setExecutor(new CosmeticsCommand(this));
        }
        if (getCommand("recall") != null) {
            getCommand("recall").setExecutor(new RecallCommand(this));
        }
        if (getCommand("cosmeticsteleport") != null) {
            getCommand("cosmeticsteleport").setExecutor(new CosmeticsTeleportCommand(this));
        }

        Bukkit.getLogger().info("[AngelCosmetics] Enabled.");
    }

    @Override
    public void onDisable() {
        if (cosmeticManager != null) {
            cosmeticManager.stop();
        }
        Bukkit.getLogger().info("[AngelCosmetics] Disabled.");
    }

    public CosmeticManager getCosmeticManager() {
        return cosmeticManager;
    }
}
