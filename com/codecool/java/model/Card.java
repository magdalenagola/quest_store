package codecool.java.model;

import java.util.Objects;

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

    public int getQuantity() {
        return quantity;
    }

    public String toString(){
        return "ID: " + getId() +
                ", title: " + getTitle() +
                ", description: " + getDescription() +
                ", quantity: " + getQuantity();
    }

    @Override
    public boolean equals(Object card2) {
        if (this == card2) return true;
        if (card2 == null || getClass() != card2.getClass()) return false;
        Card card = (Card) card2;
        return this.getId() == card.getId() &&
                this.getTitle().equals(card.getTitle()) &&
                this.getDescription().equals(card.getDescription()) &&
                this.getImage().equals(card.getImage()) &&
                this.getQuantity() == card.getQuantity() &&
                this.isActive() == card.isActive() &&
                this.getCost() == card.getCost();
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
