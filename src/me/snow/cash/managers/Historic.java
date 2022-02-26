package me.snow.cash.managers;

public class Historic {

    private String sent;
    private String target;
    private String type;
    private int amount;
    private String date;

    public Historic(String sent, String target, String type, int amount, String date) {
        this.sent = sent;
        this.target = target;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
