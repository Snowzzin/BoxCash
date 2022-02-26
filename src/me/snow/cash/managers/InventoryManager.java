package me.snow.cash.managers;

import me.snow.cash.BoxCash;
import me.snow.cash.loja.LojaManager;
import me.snow.cash.utils.Formatador;
import me.snow.cash.utils.Scroller;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Map;

public class InventoryManager {

    private int i;

    LojaManager lojaManager = BoxCash.plugin.getLojaManager();

    public Inventory viewCashBank(Player p) {

        Inventory inv = Bukkit.createInventory(null, 6 * 9, "Cash Mundial");

        inv.setItem(4, new ItemManager().createItem(Material.SKULL_ITEM, "§eSeu perfil", Arrays.asList("",
                " §7Cash: §6" + Formatador.formatNumber(BoxCash.plugin.getCashAPI().getCash(p)),
                " §7Cash Movimentado: §6" + Formatador.formatNumber(BoxCash.plugin.getCashAPI().getMovementedCash(p)),
                "",
                " §7Receber Cash: " + (BoxCash.plugin.getCashAPI().getReceivedCash(p) ? "§aAtivado" : "§cDesativado"),
                " §7Discord: §cNão vinculado",
                "",
                "§7Clique para ir a loja de Cash.")));

        inv.setItem(8, new ItemManager().createItem(Material.COMPASS, "§eLoja", Arrays.asList("",
                "§7Temos uma loja propria para gastar",
                "§7seu cash.",
                "",
                "§7Clique para abrir.")));

        inv.setItem(20, new ItemManager().createItem(Material.ARMOR_STAND, "§eReceber Cash", Arrays.asList("",
                "§7Desative o recebimento de Cash para a sua",
                "§7Conta.",
                "",
                " §7Receber Cash: " + (BoxCash.plugin.getCashAPI().getReceivedCash(p) ? "§aAtivado" : "§cDesativado"),
                "",
                "§7Clique para alterar.")));

        inv.setItem(22, new ItemManager().createItem(Material.SIGN, "§aCash Top", Arrays.asList("",
                "§7Ranking de Cash do servidor.",
                "",
                " §7Atual Cash Top: §6Ninguem",
                "",
                "§7Clique para abrir o Ranking.")));

        inv.setItem(24, new ItemManager().createItem(Material.PAPER, "§eHistorico de Transações", Arrays.asList("",
                "§7Historico de transações da sua conta.",
                "",
                "§7Clique para mais informações.")));

        inv.setItem(39, new ItemManager().createItem(Material.WATCH, "§eEnviar Cash", Arrays.asList("",
                "§7Enviar cash de uma forma rapida e facil.",
                "",
                " §cCaso queira cancelar, digite §lCANCELAR§c.",
                "",
                "§7Clique para enviar cash.")));

        return inv;
    }

    public void transactionsInventory(Player p) {
        HistoricDao.addItem(p);

        Scroller scroller = new Scroller.ScrollerBuilder().withName("Transações").withItems(HistoricDao.getPaperItems(p)).build();
        scroller.open(p);
    }

    public Inventory storeCash(Player p) {

        Inventory inv = Bukkit.createInventory(null, 6*9, "Loja - Cash");

        lojaManager.getItem().entrySet().stream().map(Map.Entry::getValue).forEach(kit -> {
            inv.setItem(kit.getSlot(), kit.getIcon());
        });

        return inv;
    }
}
