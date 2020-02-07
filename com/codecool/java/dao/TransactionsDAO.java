package codecool.java.dao;

public interface TransactionsDAO extends DAO{
    List<Transaction> loadAllNotApproved();
    List<Transaction> displayAllTransactionsByStudent(Student student);
    void addCardTransaction(CardTransaction ct);
    void addQuestTransaction(QuestTransaction qt);
    void updateStudentQuest(QuestTransaction qt);
}
