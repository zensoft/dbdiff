package database.diff.model;

/**
 * Created by tomek on 17.02.17.
 */
public class DatabasesInfo {
    private DataBaseInfo firstDatabase;
    private DataBaseInfo secondDatabase;

    public DatabasesInfo(DataBaseInfo firstDatabase, DataBaseInfo secondDatabase) {
        this.firstDatabase = firstDatabase;
        this.secondDatabase = secondDatabase;
    }

    public DataBaseInfo getFirstDatabase() {
        return firstDatabase;
    }

    public DataBaseInfo getSecondDatabase() {
        return secondDatabase;
    }
}
