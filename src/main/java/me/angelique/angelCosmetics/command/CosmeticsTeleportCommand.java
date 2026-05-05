package me.angelique.angelCosmetics.command;

import me.angelique.angelCosmetics.AngelCosmetics;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CosmeticsTeleportCommand implements CommandExecutor {

    private final AngelCosmetics plugin;

    public CosmeticsTeleportCommand(AngelCosmetics plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (args.length < 3) {
            player.sendMessage("§cUsage: /cosmeticsteleport <x> <y> <z>");
            return true;
        }

        try {
            World world = player.getWorld();
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            Location target = new Location(world, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
            plugin.getCosmeticManager().playRecall(player, target);
            return true;
        } catch (NumberFormatException ex) {
            player.sendMessage("§cCoordinates must be numbers.");
            return true;
        }
    }
}
