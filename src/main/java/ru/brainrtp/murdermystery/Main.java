package ru.brainrtp.murdermystery;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import ru.brainrtp.murdermystery.commands.Setup;
import ru.brainrtp.murdermystery.utils.ChatUtils;

public final class Main extends JavaPlugin implements Listener, PluginMessageListener {
    public static Main instance;
    public static GameState current;
    private Chat chat;
    public static Economy economy;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        if (this.getConfig().getBoolean("Setup", true)) {
            this.getCommand("setup").setExecutor((CommandExecutor) new Setup());
            return;
        }
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new ChatUtils(this), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        setupEconomy();
        setupChat();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("Connect")) {
            //TODO: дописать...
        }
    }
    private boolean setupChat() {
        final RegisteredServiceProvider<Chat> chatProvider = (RegisteredServiceProvider<Chat>) this.getServer().getServicesManager().getRegistration((Class) Chat.class);
        if (chatProvider != null) {
            this.chat = (Chat) chatProvider.getProvider();
        }
        return this.chat != null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    static {
        Main.current = GameState.WAITING;
    }
}
