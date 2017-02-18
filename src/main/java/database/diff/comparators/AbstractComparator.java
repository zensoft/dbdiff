package database.diff.comparators;

import database.diff.model.DataBaseInfo;
import database.diff.model.DatabasesInfo;

/**
 * Created by tomek on 18.02.17.
 */
public abstract class AbstractComparator implements Comparator {

    private DatabasesInfo databasesInfo;
    protected StringBuffer stringBuffer;

    public AbstractComparator(DatabasesInfo databasesInfo) {
        this.databasesInfo = databasesInfo;
        this.stringBuffer = new StringBuffer();
    }

    public DataBaseInfo getFirstDataBaseInfo() {
        return databasesInfo.getFirstDatabase();
    }

    public String getFirstDbName() {
        return "Baza '" + getFirstDataBaseInfo().getName() + "'";
    }

    public DataBaseInfo getSecondDataBaseInfo() {
        return databasesInfo.getSecondDatabase();
    }

    public String getSecondDbName() {
        return "Baza '" + getSecondDataBaseInfo().getName() + "'";
    }

}
