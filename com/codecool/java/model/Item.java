package codecool.java.model;

public abstract class Item {
    private int id;
    private String title;
    private String description;
    private String image;
    private boolean isActive;
    private int cost;

    public Item(int id, String title, String description, String image, boolean isActive, int cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.isActive = isActive;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
