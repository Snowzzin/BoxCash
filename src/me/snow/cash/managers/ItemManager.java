package me.snow.cash.managers;

import me.snow.cash.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemManager {

    public ItemStack createItem(Material mat, String name, List<String> lore) {
        return new ItemBuilder(mat).setName(name).setLore(lore).build();
    }
}
