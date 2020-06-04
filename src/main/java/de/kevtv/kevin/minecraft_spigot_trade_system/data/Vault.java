package de.kevtv.kevin.minecraft_spigot_trade_system.data;

import de.kevtv.kevin.minecraft_spigot_trade_system.Main;
import org.bukkit.entity.Player;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Economy econ = null;

    public static boolean setupEconomy() {
        if (Main.getPlugin().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Main.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static double getMoneyOfPlayer(Player player) {

        return econ.bankBalance(player.getName()).amount;
    }

    public static void addMoneyOfPlayer(Player player, double amount) {
        econ.bankDeposit(player.getName(), amount);
    }

    public static void remMoneyOfPlayer(Player player, double amount) {
        econ.bankWithdraw(player.getName(), amount);
    }

}
