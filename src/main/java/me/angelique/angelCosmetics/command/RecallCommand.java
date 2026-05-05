package me.angelique.angelCosmetics.command;

import me.angelique.angelCosmetics.AngelCosmetics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class RecallCommand implements CommandExecutor {

    private final AngelCosmetics plugin;

    public RecallCommand(AngelCosmetics plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }
        plugin.getCosmeticManager().playRecall(player, null);
        return true;
    }
}
