package database.diff.comparators;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Multimap;
import database.diff.model.DatabasesInfo;
import database.diff.utils.Utils;

import java.util.Collection;

/**
 * Created by tomek on 18.02.17.
 */
public class TablesComparator extends AbstractComparator {

    public TablesComparator(DatabasesInfo databasesInfo) {
        super(databasesInfo);
    }

    @Override
    public String getResult() {
        stringBuffer.append(getInfo() + NEW_LINE);

        String data = getDataString(firstDbSchemasTables().keySet(), commonTableInSchema());
        stringBuffer.append(data + NEW_LINE);

        data = getDataString(firstDbSchemasTables().keySet(), diffFirstDbSecondDbInSchema());
        stringBuffer.append(data + DOUBLE_NEW_LINE);

        data = getDataString(firstDbSchemasTables().keySet(), diffSecondDbFirstDbInSchema());
        stringBuffer.append(data);

        return stringBuffer.toString();
    }

    @Override
    public String getInfo() {
        return "TABELE" + NEW_LINE;
    }

    private Function<String, String> diffFirstDbSecondDbInSchema() {
        return schema -> {
            Collection<String> diffList = Utils.getDiffList(firstDbSchemasTables().get(schema), secondDbSchemasTables().get(schema));
            return Utils.getCompareInfo(diffList, getSecondDbName() + " " + schema + " nie zawiera %s");
        };
    }

    private Function<String, String> diffSecondDbFirstDbInSchema() {
        return schema -> {
            Collection<String> diffList = Utils.getDiffList(secondDbSchemasTables().get(schema), firstDbSchemasTables().get(schema));
            return Utils.getCompareInfo(diffList, getFirstDbName() + " " + schema + " nie zawiera %s");
        };
    }

    private Function<String, String> commonTableInSchema() {
        return schema -> {
            Collection<String> commonList = Utils.getCommonList(firstDbSchemasTables().get(schema), secondDbSchemasTables().get(schema));
            return Utils.getCompareInfo(commonList, "Wspolne tabele dla " + schema + " %s");
        };
    }

    private String getDataString(Collection<String> keySet, Function<String,String> func) {
        return FluentIterable.from(firstDbSchemasTables().keySet())
                .transform(s -> func.apply(s))
                .join(Joiner.on(NEW_LINE));
    }

    private Multimap<String, String> firstDbSchemasTables() {
        return getFirstDataBaseInfo().getSchemasTables();
    }

    private Multimap<String, String> secondDbSchemasTables() {
        return getSecondDataBaseInfo().getSchemasTables();
    }

}
