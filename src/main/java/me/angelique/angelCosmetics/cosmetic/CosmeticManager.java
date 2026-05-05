package me.angelique.angelCosmetics.cosmetic;

import me.angelique.angelCosmetics.AngelCosmetics;
import me.angelique.angelCosmetics.model.PlayerCosmeticState;
import me.angelique.angelCosmetics.model.RecallType;
import me.angelique.angelCosmetics.model.TrailType;
import me.angelique.angelCosmetics.model.WingType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CosmeticManager {

    private final AngelCosmetics plugin;
    private final Map<UUID, PlayerCosmeticState> states = new ConcurrentHashMap<>();
    private final Map<UUID, BukkitTask> recallTasks = new ConcurrentHashMap<>();
    private BukkitTask ambientTask;

    public CosmeticManager(AngelCosmetics plugin) {
        this.plugin = plugin;
    }

    public void start() {
        stop();
        ambientTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    renderTrail(player);
                    renderWings(player);
                }
            }
        }.runTaskTimer(plugin, 10L, 2L);
    }

    public void stop() {
        if (ambientTask != null) {
            ambientTask.cancel();
            ambientTask = null;
        }
        recallTasks.values().forEach(BukkitTask::cancel);
        recallTasks.clear();
    }

    public PlayerCosmeticState getState(UUID uuid) {
        return states.computeIfAbsent(uuid, key -> new PlayerCosmeticState());
    }

    public void cancelRecall(UUID uuid) {
        BukkitTask task = recallTasks.remove(uuid);
        if (task != null) {
            task.cancel();
        }
    }

    public void playRecall(Player player, Location target) {
        cancelRecall(player.getUniqueId());
        PlayerCosmeticState state = getState(player.getUniqueId());
        Location start = player.getLocation().clone();

        BukkitTask task = new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (!player.isOnline() || player.isDead()) {
                    cancelRecall(player.getUniqueId());
                    cancel();
                    return;
                }

                Location base = player.getLocation().clone();
                player.setVelocity(new Vector(0, 0, 0));

                spawnRecallRing(base, tick, state.getRecallType());
                spawnRecallSpiral(base, tick, state.getRecallType());

                if (tick % 10 == 0) {
                    player.getWorld().playSound(base, Sound.BLOCK_BEACON_AMBIENT, 0.7f, 1.2f);
                }

                if (tick >= 60) {
                    Location destination = target != null ? target.clone() : start.clone().add(0, 0, 0);
                    if (target != null) {
                        player.teleport(destination);
                    }
                    player.getWorld().spawnParticle(Particle.PORTAL, destination.clone().add(0, 1.0, 0), 80, 0.5, 0.8, 0.5, 0.15);
                    player.getWorld().playSound(destination, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    cancelRecall(player.getUniqueId());
                    cancel();
                }
                tick++;
            }
        }.runTaskTimer(plugin, 0L, 1L);

        recallTasks.put(player.getUniqueId(), task);
    }

    private void renderTrail(Player player) {
        TrailType type = getState(player.getUniqueId()).getTrailType();
        if (type == TrailType.NONE || !player.isOnline() || player.isDead()) {
            return;
        }

        Location loc = player.getLocation().clone().add(0, 0.1, 0);
        switch (type) {
            case FLAME -> player.getWorld().spawnParticle(Particle.FLAME, loc, 3, 0.15, 0.05, 0.15, 0.01);
            case ENCHANT -> player.getWorld().spawnParticle(Particle.ENCHANT, loc, 8, 0.25, 0.15, 0.25, 0.4);
            case HEART -> player.getWorld().spawnParticle(Particle.HEART, loc, 1, 0.2, 0.1, 0.2, 0.0);
            case TOTEM -> player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, loc, 3, 0.2, 0.15, 0.2, 0.1);
            default -> {
            }
        }
    }

    private void renderWings(Player player) {
        WingType type = getState(player.getUniqueId()).getWingType();
        if (type == WingType.NONE || !player.isOnline() || player.isDead()) {
            return;
        }

        Location center = player.getLocation().clone().add(0, 1.4, 0);
        Vector right = center.getDirection().crossProduct(new Vector(0, 1, 0)).normalize();
        if (right.lengthSquared() == 0) {
            right = new Vector(1, 0, 0);
        }

        for (double y = -0.4; y <= 0.5; y += 0.18) {
            double width = 0.7 - Math.abs(y) * 0.6;
            for (double x = 0.15; x <= width; x += 0.14) {
                Location leftLoc = center.clone().add(right.clone().multiply(-x)).add(0, y, 0);
                Location rightLoc = center.clone().add(right.clone().multiply(x)).add(0, y, 0);
                spawnWingParticle(leftLoc, type);
                spawnWingParticle(rightLoc, type);
            }
        }
    }

    private void spawnWingParticle(Location loc, WingType type) {
        switch (type) {
            case ANGEL -> loc.getWorld().spawnParticle(Particle.DUST, loc, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.2f));
            case DEMON -> loc.getWorld().spawnParticle(Particle.DUST, loc, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(180, 20, 20), 1.2f));
            case ARCANE -> {
                loc.getWorld().spawnParticle(Particle.DUST, loc, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(90, 40, 255), 1.0f));
                loc.getWorld().spawnParticle(Particle.ENCHANT, loc, 1, 0.01, 0.01, 0.01, 0.0);
            }
            default -> {
            }
        }
    }

    private void spawnRecallRing(Location base, int tick, RecallType type) {
        double radius = 1.2 + (Math.sin(tick * 0.18) * 0.1);
        for (int i = 0; i < 20; i++) {
            double angle = (Math.PI * 2 * i / 20.0) + (tick * 0.12);
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            Location point = base.clone().add(x, 0.15, z);
            spawnRecallParticle(point, type, false);
        }
    }

    private void spawnRecallSpiral(Location base, int tick, RecallType type) {
        for (int i = 0; i < 3; i++) {
            double progress = (tick * 0.12) + (i * (Math.PI * 2 / 3.0));
            double x = Math.cos(progress) * 0.6;
            double z = Math.sin(progress) * 0.6;
            double y = (tick % 30) * 0.06;
            Location point = base.clone().add(x, 0.2 + y, z);
            spawnRecallParticle(point, type, true);
        }
    }

    private void spawnRecallParticle(Location point, RecallType type, boolean elevated) {
        switch (type) {
            case BLUE -> {
                point.getWorld().spawnParticle(Particle.DUST, point, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(80, 180, 255), elevated ? 1.3f : 1.0f));
                if (elevated) {
                    point.getWorld().spawnParticle(Particle.END_ROD, point, 1, 0.02, 0.02, 0.02, 0.0);
                }
            }
            case GOLD -> {
                point.getWorld().spawnParticle(Particle.DUST, point, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(255, 210, 70), elevated ? 1.4f : 1.1f));
                point.getWorld().spawnParticle(Particle.GLOW, point, 1, 0.02, 0.02, 0.02, 0.0);
            }
            case VOID -> {
                point.getWorld().spawnParticle(Particle.DUST, point, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(120, 60, 255), elevated ? 1.3f : 1.0f));
                point.getWorld().spawnParticle(Particle.PORTAL, point, 2, 0.05, 0.05, 0.05, 0.02);
            }
            case NONE -> point.getWorld().spawnParticle(Particle.CLOUD, point, 1, 0.01, 0.01, 0.01, 0.0);
        }
    }
}
