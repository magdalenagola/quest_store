package codecool.java.dao;

public class NotInDatabaseException extends Exception{
    public NotInDatabaseException() {
        super("User not found");
    }
}
