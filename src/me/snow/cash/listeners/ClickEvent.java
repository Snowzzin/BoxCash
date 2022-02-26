package me.snow.cash.listeners;

import me.snow.cash.BoxCash;
import me.snow.cash.loja.LojaManager;
import me.snow.cash.managers.Historic;
import me.snow.cash.managers.HistoricDao;
import me.snow.cash.managers.InventoryManager;
import me.snow.cash.utils.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class ClickEvent implements Listener {

    LojaManager lojaManager = BoxCash.plugin.getLojaManager();

    @EventHandler
    void a(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory().getTitle().equals("Cash Mundial")) {
            e.setCancelled(true);

            if(e.getSlot() == 4) {
                p.openInventory(new InventoryManager().storeCash(p));
                p.sendMessage("§aLoja de Cash aberta com sucesso.w");
            }

            if(e.getSlot() == 8) {
                p.openInventory(new InventoryManager().storeCash(p));
                p.sendMessage("§aLoja de Cash aberta com sucesso.");
            }

            if(e.getSlot() == 20) {

                if (BoxCash.plugin.getCashAPI().getReceivedCash(p)) {

                    BoxCash.plugin.getCashAPI().setReceivedCash(p, 0);
                    p.sendMessage(BoxCash.plugin.mensagens.getConfig().getString("Mensagens.CashToggleOff")
                            .replace("&","§"));
                    p.closeInventory();
                    p.openInventory(new InventoryManager().viewCashBank(p));

                } else {

                    BoxCash.plugin.getCashAPI().setReceivedCash(p, 1);
                    p.sendMessage(BoxCash.plugin.mensagens.getConfig().getString("Mensagens.CashToggleOn")
                            .replace("&","§"));
                    p.closeInventory();
                    p.openInventory(new InventoryManager().viewCashBank(p));

                }
            }

            if(e.getSlot() == 39) {

                p.closeInventory();

                BoxCash.plugin.inPayMethod.add(p.getName());

                p.sendMessage("§aPara enviar seu Cash de uma forma rapida, digite");
                p.sendMessage(" ");
                p.sendMessage(" §7Jogador, Quantia");
                p.sendMessage(" ");
                p.sendMessage("§aPara cancelar, digite §lCANCELAR§a.");
            }

            if(e.getSlot() == 24) {

                if(HistoricDao.getTransactions().containsKey(p.getName())) {
                    new InventoryManager().transactionsInventory(p);

                } else {
                    p.sendMessage("§cVocê não possui nenhuma transação recente.");
                    p.closeInventory();

                }

            }
        }else{
            return;
        }
    }

    @EventHandler
    void b(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory().getTitle().equals("Loja - Cash")) {
            e.setCancelled(true);

            lojaManager.getItem().entrySet().stream().map(Map.Entry::getValue).forEach(loja -> {
                if(e.getSlot() == e.getSlot()) {

                    if(!p.hasPermission(loja.getPerm())) {
                        p.sendMessage("§cVocê não possui permissão.");
                        return;
                    }

                    if(loja.getPriceMoney() > BoxCash.plugin.getCashAPI().getCash(p)) {
                        p.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.SemCash")
                                .replace("&","§"));
                        return;
                    }

                    BoxCash.plugin.getCashAPI().removeCash(p, (int) loja.getPriceMoney());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), loja.getCommand()
                            .replace("{target}", p.getName()));

                    for(Player all: Bukkit.getOnlinePlayers()) {
                        ActionBarAPI.sendActionBarMessage(all, BoxCash.plugin.mensagens.getString("Mensagens.ComprouItem")
                                .replace("&","§")
                                .replace("{jogador}", p.getName())
                                .replace("{item}", loja.getName()));
                    }


                }
            });
        }else{
            return;
        }
    }
}
