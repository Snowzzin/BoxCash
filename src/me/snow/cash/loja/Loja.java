package me.snow.cash.loja;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Loja {

    private ItemStack icon;
    private List<String> lore;
    private String name;
    private int slot;
    private double priceMoney;
    private String perm;
    private String command;

    public Loja(ItemStack icon, List<String> lore, String name, int slot, double priceMoney, String perm, String command) {
        this.icon = icon;
        this.lore = lore;
        this.name = name;
        this.slot = slot;
        this.priceMoney = priceMoney;
        this.perm = perm;
        this.command = command;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public String getCommand() {
        return command;
    }

    public void setIcon(String command) {
        this.command = command;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public double getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(double priceMoney) {
        this.priceMoney = priceMoney;
    }
}
