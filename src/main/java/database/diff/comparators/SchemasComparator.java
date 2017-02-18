package database.diff.comparators;

import com.google.common.collect.Collections2;
import database.diff.model.DatabasesInfo;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static com.google.common.base.Predicates.*;

/**
 * Created by tomek on 17.02.17.
 */
public class SchemasComparator extends AbstractComparator {

    public SchemasComparator(DatabasesInfo databasesInfo) {
        super(databasesInfo);
    }

    public String getResult() {
        stringBuffer.append(getInfo());
        Collection<String> filter = Collections2.filter(firstDbSchemas(), in(secondDbSchemas()));
        filter.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                stringBuffer.append("czesc wspolna " + s + NEW_LINE);
            }
        });
        stringBuffer.append(NEW_LINE);
        filter = Collections2.filter(firstDbSchemas(), not(in(secondDbSchemas())));
        filter.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                stringBuffer.append(getSecondDbName() + " nie zawiera " + s + NEW_LINE);
            }
        });
        stringBuffer.append(NEW_LINE);
        filter = Collections2.filter(secondDbSchemas(), not(in(firstDbSchemas())));
        filter.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                stringBuffer.append(getFirstDbName() + " nie zawiera " + s + NEW_LINE);
            }
        });

        stringBuffer.append("======================" + NEW_LINE);

        return stringBuffer.toString();
    }

    @Override
    public String getInfo() {
        return "Schematy" + NEW_LINE;
    }

    private List<String> firstDbSchemas() {
        return getFirstDataBaseInfo().getSchemas();
    }

    private List<String> secondDbSchemas() {
        return getSecondDataBaseInfo().getSchemas();
    }

}
