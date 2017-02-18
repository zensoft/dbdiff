package database.diff.comparators;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Multimap;
import database.diff.model.DatabasesInfo;

import java.util.Collection;

import static com.google.common.base.Predicates.*;
import static com.google.common.base.Predicates.not;

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
        String data = FluentIterable.from(firstDbSchemasTables().keySet())
                .transform(s -> commonTableInSchema(s))
                .join(Joiner.on(NEW_LINE));
        stringBuffer.append(data + NEW_LINE);

        data = FluentIterable.from(firstDbSchemasTables().keySet())
                .transform(s -> diffFirstDbSecondDbInSchema(s)).join(Joiner.on(NEW_LINE));
        stringBuffer.append(data + DOUBLE_NEW_LINE);

        data = FluentIterable.from(firstDbSchemasTables().keySet())
                .transform(s -> diffSecondDbFirstDbInSchema(s))
                .join(Joiner.on(NEW_LINE));
        stringBuffer.append(data);
        stringBuffer.append("======================" + NEW_LINE);
        return stringBuffer.toString();
    }

    @Override
    public String getInfo() {
        return "Tabele" + NEW_LINE;
    }

    private String diffFirstDbSecondDbInSchema(final String schema) {
        Collection<String> filter = Collections2.filter(firstDbSchemasTables().get(schema), not(in(secondDbSchemasTables().get(schema))));
        String join = FluentIterable.from(filter)
                .transform(s -> getSecondDbName() + " " + schema + " nie zawiera " + s)
                .join(Joiner.on(NEW_LINE));
        return join;
    }

    private String diffSecondDbFirstDbInSchema(final String schema) {
        Collection<String> filter = Collections2.filter(secondDbSchemasTables().get(schema), not(in(firstDbSchemasTables().get(schema))));
        String join = FluentIterable.from(filter)
                .transform(s -> getFirstDbName() + " " + schema + " nie zawiera " + s)
                .join(Joiner.on(NEW_LINE));
        return join;
    }

    private String commonTableInSchema(String schema) {
        Collection<String> filter = Collections2.filter(firstDbSchemasTables().get(schema), in(secondDbSchemasTables().get(schema)));
        String join = FluentIterable.from(filter)
                .transform(s -> "Wspolne tabele dla " + schema + " " + s)
                .join(Joiner.on(NEW_LINE));
        return join;
    }

    private Multimap<String, String> firstDbSchemasTables() {
        return getFirstDataBaseInfo().getSchemasTables();
    }

    private Multimap<String, String> secondDbSchemasTables() {
        return getSecondDataBaseInfo().getSchemasTables();
    }

}
