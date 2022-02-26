package me.snow.cash.managers;

import me.snow.cash.BoxCash;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoricDao {

    private static final Map<String, Historic> transactions = new HashMap<>();
    private static final Map<String, List<ItemStack>> paperItems = new HashMap<>();
    private static final List<ItemStack> items = new ArrayList<>();

    public static void addTransaction(Player p, Historic historic) {
        transactions.put(p.getName(), historic);
    }

    public static void addItem(Player player) {
        Historic historic = HistoricDao.getHistoric(player);

        String name = historic.getType().equals("Enviou") ? historic.getTarget() : historic.getSent();
        String type = historic.getType().equals("Enviou") ? "Enviou" : "Recebeu";

        List<String> lore = BoxCash.plugin.itens.getStringList("Itens.Extrato.Lore");
        lore = lore.stream().map(l -> l.replace("&", "§")
                .replace("{alvo}", name)
                .replace("{quantia}", "" + historic.getAmount())
                .replace("{tipo}", type)
                .replace("{data}", historic.getDate())).collect(Collectors.toList());

        ItemStack item = new ItemManager().createItem(Material.PAPER, BoxCash.plugin.itens.getString("Itens.Extrato.Nome"), lore);

        if (!items.contains(item))
            items.add(item);

        paperItems.put(player.getName(), items);
    }

    public static Map<String, Historic> getTransactions() {
        return transactions;
    }

    public static Historic getHistoric(Player player) {
        return transactions.get(player.getName());
    }

    public static List<ItemStack> getPaperItems(Player player) {
        if (paperItems.containsKey(player.getName()))
            return paperItems.get(player.getName());

        return new ArrayList<>();
    }

}
