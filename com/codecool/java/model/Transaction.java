package codecool.java.model;

public abstract class Transaction {
    private int itemId;
    private int userId;
    private String date;
    private int cost;

    public Transaction(int itemId, int userId, String date, int cost) {
        this.itemId = itemId;
        this.userId = userId;
        this.date = date;
        this.cost = cost;
    }

    @Override
    public String toString()  {
        return "itemID: " + itemId + ", userID: " + userId + ", date: " + date + ", cost: " + cost;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}