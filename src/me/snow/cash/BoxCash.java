package me.snow.cash;

import me.snow.cash.api.CashAPI;
import me.snow.cash.commands.CashCommand;
import me.snow.cash.listeners.ChatEvent;
import me.snow.cash.listeners.ClickEvent;
import me.snow.cash.listeners.JoinEvent;
import me.snow.cash.loja.LojaManager;
import me.snow.cash.services.SQLite;
import me.snow.cash.utils.DateManager;
import me.snow.cash.utils.S_Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoxCash extends JavaPlugin {

    public static BoxCash plugin;

    private SQLite sqLite;

    private CashAPI cashAPI;

    private LojaManager lojaManager;

    public S_Config mensagens = new S_Config(this, "mensagens.yml");
    public S_Config itens = new S_Config(this, "itens.yml");

    public final List<String> inPayMethod = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        mensagens.saveDefaultConfig();
        itens.saveDefaultConfig();
        setupSQL();

        registerObjects();
        registerEvents();
        registerCommands();

        Bukkit.getConsoleSender().sendMessage("§6[BoxCash] §ePlugin iniciado");
    }

    @Override
    public void onDisable() {
        sqLite.closeConnection();
    }

    public void registerCommands() {
        getCommand("cash").setExecutor(new CashCommand());
    }

    private void registerObjects() {
        lojaManager = new LojaManager(getConfig());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
    }

    private void setupSQL() {
        DateManager.createFolder("database");

        sqLite = new SQLite();

        this.cashAPI = new CashAPI(this);
    }

    public CashAPI getCashAPI() {
        return this.cashAPI;
    }

    public SQLite getSqLite() {
        return this.sqLite;
    }

    public LojaManager getLojaManager() {
        return this.lojaManager;
    }
}
