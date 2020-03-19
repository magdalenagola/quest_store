package codecool.java.model;

public class CannotAffordCardException extends Exception{
    public CannotAffordCardException() {
        super("Cannot afford card");
    }
}
