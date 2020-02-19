package codecool.java.dao;

import codecool.java.model.Student;
import codecool.java.model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionsDAO extends DAO{
    List<Transaction> loadAllNotApproved() throws SQLException;
    List<Transaction> displayAllTransactionsByStudent(Student student) throws SQLException;
}
