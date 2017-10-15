package ru.brainrtp.murdermystery;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Создано 18.09.17
 */

public class Settings {

    private static Main plugin = Main.instance;
    public static final String prefix;
    static final String spawnLocation;
    public static final String adminChat;
    public static final String moneycat;
    public static final String defaultChat;
    public static final String vipChat;
    public static final String premiumChat;
    public static final String legendChat;
    public static final String eliteChat;
    public static final String sponsorChat;
    public static final int slots;
    public static final String lobby;
    static List<String> motd = new ArrayList<>();
    static final boolean orthography;

    static {
        prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
        orthography = plugin.getConfig().getBoolean("orthography");
        spawnLocation =ChatColor.translateAlternateColorCodes('&',  plugin.getConfig().getString("spawnLocation"));
        adminChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.admin"));
        defaultChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.default"));
        vipChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.vip"));
        premiumChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.premium"));
        legendChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.legend"));
        eliteChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.elite"));
        sponsorChat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.sponsor"));
        moneycat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ChatUtils.moneycat"));
        slots = plugin.getConfig().getInt("Slots");
        lobby = plugin.getConfig().getString("Lobby");
        motd = (List<String>) plugin.getConfig().getList("Motd");
    }
}
