package codecool.java.dao;

import codecool.java.model.Student;
import codecool.java.model.Transaction;

import java.util.List;

public interface TransactionsDAO extends DAO{
    List<Transaction> loadAllNotApproved();
    List<Transaction> displayAllTransactionsByStudent(Student student);
}
