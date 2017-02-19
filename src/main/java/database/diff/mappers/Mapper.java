package database.diff.mappers;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.util.List;
import java.util.Map;

/**
 * Created by tomek on 16.02.17.
 */
public class Mapper {

    public static List<String> getSchemasMapper(List<Map<String, Object>> list) {
        return FluentIterable.from(list)
                .transform(new Function<Map<String, Object>, String>() {
                    @Override
                    public String apply(Map<String, Object> stringObjectMap) {
                        return stringObjectMap.get("table_schema") + "";
                    }
                })
                .toList();
    }

    public static List<String> getIndexesMapper(List<Map<String, Object>> list) {
        return FluentIterable.from(list)
                .transform(new Function<Map<String, Object>, String>() {
                    @Override
                    public String apply(Map<String, Object> stringObjectMap) {
                        return stringObjectMap.get("table_name") + "-" + stringObjectMap.get("index_name");
                    }
                })
                .toList();
    }

}
