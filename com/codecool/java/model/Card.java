package codecool.java.model;

public class Card {
    private int Id;
    private int cost;
    private String description;
    private String imageName;
    private boolean isActive;
    private int quantity;
    private String title;

    public Card(int id, int cost, String description, String imageName, boolean isActive, int quantity, String title) {
        Id = id;
        this.cost = cost;
        this.description = description;
        this.imageName = imageName;
        this.isActive = isActive;
        this.quantity = quantity;
        this.title = title;
    }

    public Card(int cost, String description, String imageName, boolean isActive, int quantity, String title) {
        this.cost = cost;
        this.description = description;
        this.imageName = imageName;
        this.isActive = isActive;
        this.quantity = quantity;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return Id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString(){
        return "ID: " + getId() +
                ", title: " + getTitle() +
                ", description: " + getDescription() +
                ", quantity: " + getQuantity();
    }
}
