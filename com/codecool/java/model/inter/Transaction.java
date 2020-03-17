package codecool.java.model;

import java.sql.Date;
import java.sql.SQLException;

public abstract class Transaction {
    private Item item;
    private int userId;
    private Date date;
    private int cost;

    public Transaction(Item item, int userId, Date date, int cost) {
        this.item = item;
        this.userId = userId;
        this.date = date;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "itemID: " + item.getId() + ", userID: " + userId + ", date: " + date + ", cost: " + cost;
    }

    public int getItemId() {
        return item.getId();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}