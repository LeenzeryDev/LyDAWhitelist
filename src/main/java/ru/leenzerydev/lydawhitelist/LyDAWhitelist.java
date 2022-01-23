package ru.leenzerydev.lydawhitelist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.omashune.donationalertsapi.event.AsyncDonationEvent;
import ru.omashune.donationalertsapi.model.Currency;
import ru.omashune.donationalertsapi.model.Donation;

public final class LyDAWhitelist extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onDonation(AsyncDonationEvent e) {
        Donation donation = e.getDonation();
        String username = donation.getUsername();

        Currency currency = donation.getCurrency();
        int amount = donation.getAmountMain();

        if (currency == Currency.RUB && amount >= config.getInt("amount")) {
            Bukkit.getScheduler().runTask(this, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), config.getString("whitelist-command") + " " + username);
            });
        }
    }
}
