package me.snow.cash.listeners;

import me.snow.cash.BoxCash;
import me.snow.cash.managers.Historic;
import me.snow.cash.managers.HistoricDao;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatEvent implements Listener {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

    @EventHandler
    void a(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        String msg = e.getMessage();

        if(BoxCash.plugin.inPayMethod.contains(p.getName())) {

            if(msg.equals("cancelar")) {
                e.setCancelled(true);
                BoxCash.plugin.inPayMethod.remove(p.getName());
                p.sendMessage("§cProcesso cancelado.");
                return;
            }

            Player target = Bukkit.getPlayerExact(msg.split(",")[0]);
            int quantia = Integer.parseInt(msg.split(", ")[1]);

            if(quantia > BoxCash.plugin.getCashAPI().getCash(p)) {
                BoxCash.plugin.inPayMethod.remove(p.getName());
                p.sendMessage("§cVocê não possui essa quantia de Cash.");
                return;
            }

            if(target == null) {
                p.sendMessage("§cEsse jogador não existe ou esta offline.");
                BoxCash.plugin.inPayMethod.remove(p.getName());
                return;
            }

            BoxCash.plugin.getCashAPI().addCash(target, quantia);
            BoxCash.plugin.getCashAPI().removeCash(p, quantia);

            BoxCash.plugin.getCashAPI().addMovementedCash(p, quantia);
            BoxCash.plugin.getCashAPI().addMovementedCash(target, quantia);

            Historic historicSent = new Historic(p.getName(), target.getName(), "Enviou", quantia, dateFormat.format(new Date()));
            Historic historicTarget = new Historic(p.getName(), target.getName(), "Recebeu", quantia, dateFormat.format(new Date()));

            HistoricDao.getTransactions().put(p.getName(), historicSent);
            HistoricDao.getTransactions().put(target.getName(), historicTarget);

            p.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.CashEnviado")
                    .replace("&","§")
                    .replace("{quantia}", "" + quantia)
                    .replace("{player}", target.getName()));

            target.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.CashRecebido")
                    .replace("&","§")
                    .replace("{quantia}", "" + quantia)
                    .replace("{player}", p.getName()));
            BoxCash.plugin.inPayMethod.remove(p.getName());
        }
    }
}
