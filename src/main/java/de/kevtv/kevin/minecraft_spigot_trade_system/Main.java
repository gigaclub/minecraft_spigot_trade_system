package de.kevtv.kevin.minecraft_spigot_trade_system;

import de.kevtv.kevin.minecraft_spigot_trade_system.commands.TradeAcceptCommand;
import de.kevtv.kevin.minecraft_spigot_trade_system.commands.TradeCommand;
import de.kevtv.kevin.minecraft_spigot_trade_system.commands.TradeDenyCommand;
import de.kevtv.kevin.minecraft_spigot_trade_system.config.TextConfig;
import de.kevtv.kevin.minecraft_spigot_trade_system.data.Vault;
import de.kevtv.kevin.minecraft_spigot_trade_system.listener.InventoryListener;
import de.kevtv.kevin.minecraft_spigot_trade_system.listener.TradeAcceptListener;
import de.kevtv.kevin.minecraft_spigot_trade_system.tradeinventory.TradeInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;

    /**
     * Der erste Aufruf wenn das Plugin geladen wird
     */
    @Override
    public void onEnable() {
        // Plugin startup logic

        setPlugin(this);

        if (!Vault.setupEconomy() ) {
            System.out.println((String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName())));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setTextConfig();
        registerCommands();
        registerEvents();

    }

    /**
     * Der letzte Aufruf wenn z.B. der Server beendet wird
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Getter für die Main Instanz
     *
     * @return return plugin
     */
    public static Main getPlugin() {
        return plugin;
    }

    /**
     * Setze Instanz der Main Klasse
     *
     * @param plugin set plugin
     */
    private static void setPlugin(Main plugin) {
        Main.plugin = plugin;
    }

    /**
     * Setze die Standard Antworten z.B. Fehlermeldungen bei Commands
     */
    private void setTextConfig() {
        TextConfig.createTextConfig();

        TradeCommand.registerTexts();
        TradeAcceptCommand.registerTexts();
        TradeDenyCommand.registerTexts();
        TradeAcceptListener.registerTexts();
        TradeInventory.registerTexts();
        InventoryListener.registerTexts();

        TextConfig.save();

        System.out.println("Set config files");
    }

    /**
     * Registriere Commands für User
     */
    private void registerCommands() {
        Objects.requireNonNull(getCommand("trade")).setExecutor(new TradeCommand());
        Objects.requireNonNull(getCommand("tradeaccept")).setExecutor(new TradeAcceptCommand());
        Objects.requireNonNull(getCommand("tradedeny")).setExecutor(new TradeDenyCommand());
    }

    /**
     * Registriere Events
     */
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new TradeAcceptListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

}
