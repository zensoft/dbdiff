package database.diff.comparators;

import database.diff.model.DatabasesInfo;
import database.diff.utils.Utils;

import java.util.Collection;
import java.util.List;

import static database.diff.utils.Utils.getCompareInfo;

/**
 * Created by tomek on 19.02.17.
 */
public class IndexesComparator extends AbstractComparator {

    public IndexesComparator(DatabasesInfo databasesInfo) {
        super(databasesInfo);
    }

    @Override
    public String getResult() {
        stringBuffer.append(getInfo() + NEW_LINE);

        Collection<String> commonList = Utils.getCommonList(firstDbIndexes(), secondDbIndexes());
        String commonString = getCompareInfo(commonList, "czesc wspolna %s");
        stringBuffer.append(commonString);
        stringBuffer.append(DOUBLE_NEW_LINE);

        Collection<String> diffDb1 = Utils.getDiffList(firstDbIndexes(), secondDbIndexes());
        String diffDb1String = getCompareInfo(diffDb1, getSecondDbName() + " nie zawiera %s");
        stringBuffer.append(diffDb1String);
        stringBuffer.append(DOUBLE_NEW_LINE);

        Collection<String> diffDb2 = Utils.getDiffList(secondDbIndexes(), firstDbIndexes());
        String diffDb2String = getCompareInfo(diffDb2, getFirstDbName() + " nie zawiera %s");
        stringBuffer.append(diffDb2String);
        stringBuffer.append(DOUBLE_NEW_LINE);

        return stringBuffer.toString();
    }

    private List<String> firstDbIndexes() {
        return getFirstDataBaseInfo().getIndexes();
    }

    private List<String> secondDbIndexes() {
        return getSecondDataBaseInfo().getIndexes();
    }

    @Override
    public String getInfo() {
        return "INDEKSY" + NEW_LINE;
    }
}
