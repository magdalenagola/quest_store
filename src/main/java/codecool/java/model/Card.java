package codecool.java.model;

public class Card extends Item {
    private int quantity;

    public Card(int id, int cost, String description, String imageName, boolean isActive, int quantity, String title) {
        super(id,title,description,imageName,isActive,cost);
        this.quantity = quantity;

    }
    public Card(int cost, String description, String imageName, boolean isActive, int quantity, String title) {
        super(0,title,description,imageName,isActive,cost);
        this.quantity = quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
