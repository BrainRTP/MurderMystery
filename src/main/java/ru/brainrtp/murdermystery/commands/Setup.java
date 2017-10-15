package ru.brainrtp.murdermystery.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.brainrtp.murdermystery.Main;
import ru.brainrtp.murdermystery.Settings;
import ru.brainrtp.murdermystery.utils.LocationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setup implements CommandExecutor, Listener
{
    Map<Integer, Location> locations;

    public Setup() {
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
        this.locations = new HashMap<Integer, Location>();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cВы не игрок");
            return false;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("admin")) {
            player.sendMessage("§cНет прав!");
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(Settings.prefix + "Настройка:");
            player.sendMessage("§c* §fУстановить локацию - §c/setup [Число]");
            player.sendMessage("§c* §fСохранить локации - §c/setup save");
            player.sendMessage("§c* §fУстановить лобби - §c/setup setlobby");
            player.sendMessage("§c* §fУстановить респавн для наблюдателей - §c/setup setspectator");
            player.sendMessage("§c* §fЗагрузить мир (требуется перезагрузка сервера) - §c/setup loadworld [Мир]");
            player.sendMessage("§c* §fТелепортироваться в нужный мир - §c/setup tp [Мир]");
            return false;
        }
        final String s2 = args[0];
        switch (s2) {
            case "save": {
                this.save();
                break;
            }
            case "setlobby": {
                Main.instance.getConfig().set("Lobby", LocationUtils.locationToString(player.getLocation(), true));
                Main.instance.saveConfig();
                break;
            }
            case "setspectator": {
                Main.instance.getConfig().set("Spectator", LocationUtils.locationToString(player.getLocation(), true));
                Main.instance.saveConfig();
                break;
            }
            case "loadworld": {
                final String create = args[1];
                Main.instance.getConfig().set("loadWorld", create);
                Main.instance.saveConfig();
                break;
            }
            case "tp": {
                final String world = args[1];
                if (world != null) {
                    player.teleport(new Location(Bukkit.getWorld(world), 0.0, 80.0, 0.0));
                    break;
                }
                final World nworld = Bukkit.createWorld(new WorldCreator(world).generator("EmptyGenerator"));
                player.teleport(new Location(nworld, 0.0, 80.0, 0.0));
                break;
            }
            default: {
                final int i = Integer.parseInt(args[0]);
                final Location loc = player.getLocation();
                this.locations.put(i, loc);
                player.sendMessage(String.format("Локация %s установлена", s2));
                break;
            }
        }
        player.sendMessage(Settings.prefix + "Операция успешно выполнена!");
        return true;
    }

    private void save() {
        final FileConfiguration config = Main.instance.getConfig();
        final List<String> list = new ArrayList<String>();
        for (final Location loc : this.locations.values()) {
            list.add(LocationUtils.locationToString(loc, true));
        }
        config.set("Spawns", list);
        Main.instance.saveConfig();
    }
}
