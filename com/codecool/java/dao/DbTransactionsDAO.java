package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;

import java.util.List;

public class DbTransactionsDAO implements TransactionsDAO{
    private BasicConnectionPool pool;

    public DbTransactionsDAO(){
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }

    @Override
    public List<Transaction> loadAllNotApproved() {
        return null;
    }

    @Override
    public List<Transaction> displayAllTransactionsByStudent(Student student) {
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
