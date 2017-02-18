package database.diff.comparators;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import database.diff.model.DatabasesInfo;

import java.util.List;

/**
 * Created by tomek on 17.02.17.
 */
public class DatabasesComparator {

    private DatabasesInfo databasesInfo;
    private List<AbstractComparator> comparators;

    public DatabasesComparator(DatabasesInfo databasesInfo, List<AbstractComparator> comparators) {
        this.databasesInfo = databasesInfo;
        this.comparators = comparators;
    }

    public void printResult() {
        String results = FluentIterable.from(comparators)
                .transform(abstractComparator -> abstractComparator.getResult())
                .join(Joiner.on("\n"));
        System.out.println(results);
    }

    public DatabasesInfo getDatabasesInfo() {
        return databasesInfo;
    }
}
