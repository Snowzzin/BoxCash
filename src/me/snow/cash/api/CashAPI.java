package me.snow.cash.api;

import me.snow.cash.BoxCash;
import me.snow.cash.services.SQLite;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashAPI {

    protected BoxCash main;

    private final SQLite sql;

    public CashAPI(BoxCash main) {
        this.main = main;
        this.sql = main.getSqLite();
    }

    public boolean hasAccount(Player p) {
        try {
            PreparedStatement preparedStatement = this.sql.getConnection()
                    .prepareStatement("SELECT * FROM cash WHERE player='" + p.getName() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCash(Player p) {
        try {
            PreparedStatement ps = this.sql.getConnection()
                    .prepareStatement("SELECT * FROM cash WHERE player='" + p.getName()  + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("cash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMovementedCash(Player p) {
        try {
            PreparedStatement ps = this.sql.getConnection()
                    .prepareStatement("SELECT * FROM cash WHERE player='" + p.getName()  + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("movementedCash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Boolean getReceivedCash(Player p) {
        try {
            PreparedStatement ps = this.sql.getConnection()
                    .prepareStatement("SELECT * FROM cash WHERE player='" + p.getName()  + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getBoolean("receiveCash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer setReceivedCash(Player p, Integer value) {

        this.sql.executeQuery("UPDATE cash SET receiveCash=" + value + " WHERE player='" + p.getName()  + "'");

        return value;
    }

    public Integer addMovementedCash(Player p, Integer value) {

        int movementedCash = getMovementedCash(p) + value;

        this.sql.executeQuery("UPDATE cash SET movementedCash=" + movementedCash + " WHERE player='" + p.getName()  + "'");

        return value;
    }

    public Integer addCash(Player p, Integer value) {

        int cashTotal = getCash(p) + value;

        this.sql.executeQuery("UPDATE cash SET cash=" + cashTotal + " WHERE player='" + p.getName()  + "'");

        return value;
    }

    public Integer removeCash(Player p, Integer value) {

        int cashTotal = getCash(p) - value;

        this.sql.executeQuery("UPDATE cash SET cash=" + cashTotal + " WHERE player='" + p.getName()  + "'");

        return value;
    }
}
