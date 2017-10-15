package ru.brainrtp.murdermystery.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import ru.brainrtp.murdermystery.GameState;
import ru.brainrtp.murdermystery.Main;
import ru.brainrtp.murdermystery.Settings;
import ru.brainrtp.murdermystery.utils.LocationUtils;

/**
 * Создано 16.10.17
 */

public class Waiting implements Listener {

    public static boolean started;

    public Waiting() {
        Main.current = GameState.WAITING;
        Waiting.started = false;
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
    }

    @EventHandler
    public void onLogin(final PlayerLoginEvent e) {
        if (Bukkit.getOnlinePlayers().size() == Settings.slots) {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.RED + "Нет мест");
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player player = e.getPlayer();
        final int locY = player.getLocation().getBlockY();
        if (locY < -1) {
            player.teleport(LocationUtils.stringToLocation(Settings.lobby));
        }
    }

    @EventHandler
    public void onEntitySpawn(final EntitySpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFood(final FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(final PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(final ItemSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(final PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(final InventoryClickEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    public void onPing(final ServerListPingEvent e) {
        if (Main.current == GameState.WAITING) {
            e.setMotd(ChatColor.BLUE + "Ожидание...");
        }
        else if (Main.current == GameState.STARTING) {
            e.setMotd(ChatColor.DARK_PURPLE + "Начинается...");
        }
        else if (Main.current == GameState.GAME) {
            e.setMotd(ChatColor.RED + "Идёт.");
        }
    }

}