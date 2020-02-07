package codecool.java.dao;

import java.util.List;

public class DbTransactionsDAO implements TransactionsDAO{

    @Override
    public List<Transactions> loadAllNotApproved() {
        return null;
    }

    @Override
    public List<Transactions> displayAllTransactionsByStudent(Student student) {
        return null;
    }

    @Override
    public void addCardTransaction(CardTransaction ct) {

    }

    @Override
    public void addQuestTransaction(QuestTransaction qt) {

    }

    @Override
    public void updateStudentQuest(QuestTransaction qt) {

    }

    @Override
    public void save(Object o) {

    }

    @Override
    public List loadAll() {
        return null;
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void disable(Object o) {

    }

    @Override
    public void activate(Object o) {

    }
}
