package me.angelique.angelCosmetics.command;

import me.angelique.angelCosmetics.AngelCosmetics;
import me.angelique.angelCosmetics.gui.CosmeticsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CosmeticsCommand implements CommandExecutor {

    private final AngelCosmetics plugin;

    public CosmeticsCommand(AngelCosmetics plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }
        CosmeticsMenu.open(plugin, player);
        return true;
    }
}
