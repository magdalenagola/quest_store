package codecool.java.dao;

import codecool.java.model.Student;
import codecool.java.model.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TransactionsDAO extends DAO{
    List<Transaction> loadAllNotApproved() throws SQLException;
    Map<String,List<Transaction>> displayAllTransactionsByStudent(Student student);
}
