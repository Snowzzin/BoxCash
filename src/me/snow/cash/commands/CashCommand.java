package me.snow.cash.commands;

import me.snow.cash.BoxCash;
import me.snow.cash.managers.Historic;
import me.snow.cash.managers.HistoricDao;
import me.snow.cash.managers.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CashCommand implements CommandExecutor {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas para jogadores validos.");
            return true;
        }

        Player p = (Player) sender;

        if (cmd.getLabel().equalsIgnoreCase("cash")) {
            if (args.length == 0) {
                p.openInventory(new InventoryManager().viewCashBank(p));
                return true;
            }

            if (args[0].equalsIgnoreCase("toggle")) {

                if (BoxCash.plugin.getCashAPI().getReceivedCash(p)) {

                    BoxCash.plugin.getCashAPI().setReceivedCash(p, 0);
                    p.sendMessage(BoxCash.plugin.mensagens.getConfig().getString("Mensagens.CashToggleOff")
                            .replace("&", "§"));

                } else {

                    BoxCash.plugin.getCashAPI().setReceivedCash(p, 1);
                    p.sendMessage(BoxCash.plugin.mensagens.getConfig().getString("Mensagens.CashToggleOn")
                            .replace("&", "§"));

                }
            }

            if (p.hasPermission(BoxCash.plugin.getConfig().getString("Permissões.Admin"))) {
                if (args[0].equalsIgnoreCase("adicionar")) {

                    Player target;
                    Integer quantia;

                    try {
                        target = Bukkit.getPlayerExact(args[1]);
                    } catch (Throwable ignored) {
                        p.sendMessage("§cO jogador alvo não pode ser nullo!");
                        return true;
                    }

                    try {
                        quantia = Integer.parseInt(args[2]);
                    } catch (Throwable ignored) {
                        p.sendMessage("§cA quantia não pode ser nullo!");
                        return true;
                    }

                    BoxCash.plugin.getCashAPI().addCash(target, quantia);
                    p.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.CashAdicionado")
                            .replace("&", "§")
                            .replace("{quantia}", "" + quantia)
                            .replace("{player}", target.getName()));
                }

                if (args[0].equalsIgnoreCase("remover")) {

                    Player target;
                    Integer quantia;

                    try {
                        target = Bukkit.getPlayerExact(args[1]);
                    } catch (Throwable ignored) {
                        p.sendMessage("§cO jogador alvo não pode ser nullo!");
                        return true;
                    }

                    try {
                        quantia = Integer.parseInt(args[2]);
                    } catch (Throwable ignored) {
                        p.sendMessage("§cA quantia não pode ser nullo!");
                        return true;
                    }

                    BoxCash.plugin.getCashAPI().removeCash(target, quantia);

                    p.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.CashRemovido")
                            .replace("&", "§")
                            .replace("{quantia}", "" + quantia)
                            .replace("{player}", target.getName()));
                }
            } else {
                p.sendMessage("§cVocê não possui permissão.");
                return true;
            }

            if (args[0].equalsIgnoreCase("enviar")) {

                Player target;
                int quantia;

                try {
                    target = Bukkit.getPlayerExact(args[1]);
                } catch (Throwable ignored) {
                    p.sendMessage("§cO jogador alvo não pode ser nullo!");
                    return true;
                }

                try {
                    quantia = Integer.parseInt(args[2]);
                } catch (Throwable ignored) {
                    p.sendMessage("§cA quantia não pode ser nullo!");
                    return true;
                }

                if (!BoxCash.plugin.getCashAPI().getReceivedCash(p)) {
                    p.sendMessage("§cEsse jogador desativou o recebimento de Cash");
                    return true;
                }

                if (quantia > BoxCash.plugin.getCashAPI().getCash(p)) {
                    p.sendMessage("§cVocê não possui essa quantia de Cash");
                    return true;
                }

                BoxCash.plugin.getCashAPI().addCash(target, quantia);
                BoxCash.plugin.getCashAPI().removeCash(p, quantia);

                BoxCash.plugin.getCashAPI().addMovementedCash(p, quantia);
                BoxCash.plugin.getCashAPI().addMovementedCash(target, quantia);

                Historic historicSent = new Historic(p.getName(), target.getName(), "Enviou", quantia, dateFormat.format(new Date()));
                Historic historicTarget = new Historic(p.getName(), target.getName(), "Recebeu", quantia, dateFormat.format(new Date()));

                HistoricDao.getTransactions().put(p.getName(), historicSent);
                HistoricDao.getTransactions().put(target.getName(), historicTarget);

                // ------------------------------------------------------------- //

                p.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.CashEnviado")
                        .replace("&", "§")
                        .replace("{quantia}", "" + quantia)
                        .replace("{player}", target.getName()));

                target.sendMessage(BoxCash.plugin.mensagens.getString("Mensagens.CashRecebido")
                        .replace("&", "§")
                        .replace("{quantia}", "" + quantia)
                        .replace("{player}", p.getName()));

                // ------------------------------------------------------------- //
            }

            if(args[0].equalsIgnoreCase("help")) {

                if(p.hasPermission(BoxCash.plugin.getConfig().getString("Permissões.Admin"))) {

                    p.sendMessage(" ");
                    p.sendMessage("§6BoxCash §8-> §eLista de Comandos");
                    p.sendMessage(" ");
                    p.sendMessage(" §e/cash §7- §eAbra o menu principal.");
                    p.sendMessage(" §e/cash enviar §7- §eEnvie uma quantia de cash.");
                    p.sendMessage(" §e/cash toggle §7- §eDesative o recebimento de cash.");
                    p.sendMessage(" §e/cash adicionar §7- §eAdicione cash para alguem.");
                    p.sendMessage(" §e/cash remover §7- §eRemova cash para alguem.");
                    p.sendMessage(" ");


                }else{

                    p.sendMessage(" ");
                    p.sendMessage("§6BoxCash §8-> §eLista de Comandos");
                    p.sendMessage(" ");
                    p.sendMessage(" §e/cash §7- §eAbra o menu principal.");
                    p.sendMessage(" §e/cash enviar §7- §eEnvie uma quantia de cash.");
                    p.sendMessage(" §e/cash toggle §7- §eDesative o recebimento de cash.");
                    p.sendMessage(" ");


                }
            }

        }
        return false;
    }
}
