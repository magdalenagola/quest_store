package codecool.java.dao;

public interface TransactionsDAO {
    List<Transactions> loadAllNotApproved();
    List<Transactions> displayAllTransactionsByStudent(Student student);
    void addCardTransaction(CardTransaction ct);
    void addQuestTransaction(QuestTransaction qt);
    void updateStudentQuest(QuestTransaction qt);
}
