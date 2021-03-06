package database.diff.comparators;

import database.diff.model.DatabasesInfo;
import database.diff.utils.Utils;

import java.util.Collection;
import java.util.List;

import static database.diff.utils.Utils.getCompareInfo;

/**
 * Created by tomek on 17.02.17.
 */
public class SchemasComparator extends AbstractComparator {

    public SchemasComparator(DatabasesInfo databasesInfo) {
        super(databasesInfo);
    }

    public String getResult() {
        stringBuffer.append(getInfo());

        Collection<String> commonList = Utils.getCommonList(firstDbSchemas(), secondDbSchemas());
        String commonString = getCompareInfo(commonList, "czesc wspolna %s");
        stringBuffer.append(commonString);
        stringBuffer.append(DOUBLE_NEW_LINE);

        Collection<String> diffDb1 = Utils.getDiffList(firstDbSchemas(), secondDbSchemas());
        String diffDb1String = getCompareInfo(diffDb1, getSecondDbName() + " nie zawiera %s");
        stringBuffer.append(diffDb1String);
        stringBuffer.append(DOUBLE_NEW_LINE);

        Collection<String> diffDb2 = Utils.getDiffList(secondDbSchemas(), firstDbSchemas());
        String diffDb2String = getCompareInfo(diffDb2, getFirstDbName() + " nie zawiera %s");
        stringBuffer.append(diffDb2String);
        stringBuffer.append(DOUBLE_NEW_LINE);

        return stringBuffer.toString();
    }

    @Override
    public String getInfo() {
        return "SCHEMATY" + NEW_LINE;
    }

    private List<String> firstDbSchemas() {
        return getFirstDataBaseInfo().getSchemas();
    }

    private List<String> secondDbSchemas() {
        return getSecondDataBaseInfo().getSchemas();
    }

}
