package codecool.java.dao;

import codecool.java.model.Student;

public interface TransactionsDAO extends DAO{
    List<Transaction> loadAllNotApproved();
    List<Transaction> displayAllTransactionsByStudent(Student student);
}
