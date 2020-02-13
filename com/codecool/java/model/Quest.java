package codecool.java.model;

public class Quest extends Item {
    private String category;

    public Quest(int id, String title, String description, String image, int quantity, boolean isActive, int cost, String category) {
        super(id, title, description, image, quantity, isActive, cost);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
} 
