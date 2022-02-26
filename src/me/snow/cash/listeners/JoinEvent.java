package me.snow.cash.listeners;

import me.snow.cash.BoxCash;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.Terminal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    void a(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if(!BoxCash.plugin.getCashAPI().hasAccount(p)) {
            BoxCash.plugin.getSqLite().createPlayerInDB(p);
            if(BoxCash.plugin.getConfig().getBoolean("debug")) {
                Bukkit.getConsoleSender().sendMessage("§6[BoxCash] §f[DEBUG] §eO jogador §f" + p.getName() + " §efoi registrado");
            }
        }
    }
}
