package codecool.java.model;

public class Card {
    private int Id;
    private float cost;
    private String description;
    private String imageName;
    private boolean isActive;
    private int quantity;
    private String title;

    public Card(int id, float cost, String description, String imageName, boolean isActive, int quantity, String title) {
        Id = id;
        this.cost = cost;
        this.description = description;
        this.imageName = imageName;
        this.isActive = isActive;
        this.quantity = quantity;
        this.title = title;
    }
}
