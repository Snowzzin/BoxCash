package me.snow.cash.loja;

import me.snow.cash.BoxCash;
import me.snow.cash.utils.Formatador;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LojaManager {

    private static Map<String, Loja> itemMap;

    private FileConfiguration config;

    public LojaManager(FileConfiguration config) {
        itemMap = new HashMap<>();

        this.config = config;

        setupItem();
    }

    public void createItem(String path, Loja item) {
        itemMap.put(path, item);
    }

    public Map<String, Loja> getItem() {
        return itemMap;
    }

    public Loja getItem(ItemStack item) {
        return itemMap.entrySet().stream().map(Map.Entry::getValue).filter(it -> it.getIcon().isSimilar(item)).findFirst().orElse(null);
    }

    private void setupItem() {
        for (String path : BoxCash.plugin.getConfig().getConfigurationSection("Loja").getKeys(false)) {
            ConfigurationSection key = BoxCash.plugin.getConfig().getConfigurationSection("Loja." + path);

            String name = key.getString("Nome").replace("&", "§");
            int material = Integer.parseInt(key.getString("Material").split(":")[0]);
            int data = Integer.parseInt(key.getString("Material").split(":")[1]);
            double priceMoney = key.getDouble("Preço");
            String perm = key.getString("Permissão");
            String command = key.getString("Comando");
            List<String> lore = key.getStringList("Lore");
            lore = lore.stream().map(l -> l.replace("&", "§").replace("{valor}", "" + Formatador.formatNumber(priceMoney))).collect(Collectors.toList());
            int slot = key.getInt("Slot");
            ItemStack itemBuild = new ItemStack(material, 1, (short) data);
            ItemMeta itemMeta = itemBuild.getItemMeta();
            itemMeta.setDisplayName(name);
            itemMeta.setLore(lore);
            itemBuild.setItemMeta(itemMeta);

            Loja item = new Loja(itemBuild, lore, name, slot, priceMoney, perm, command);
            createItem(path, item);
        }
    }
}
