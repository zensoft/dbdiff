package database.diff.managers;

import com.google.common.collect.*;
import database.diff.constant.Queries;
import database.diff.mappers.Mapper;
import database.diff.model.DataBaseInfo;
import database.diff.model.DbCredentials;
import database.diff.utils.Utils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.List;
import java.util.Map;

import static database.diff.model.DataBaseInfo.*;
import static database.diff.utils.Utils.toStr;

/**
 * Created by tomek on 16.02.17.
 */
public class DataBaseInfoManager {

    private DBI dbi;
    private DbCredentials dbCredentials;

    public DataBaseInfoManager(DBI dbi, DbCredentials dbCredentials) {
        this.dbi = dbi;
        this.dbCredentials = dbCredentials;
    }


    public DataBaseInfo createDataBaseInfo() throws Exception{
        try (Handle handle = this.dbi.open()){
            List<String> schemas = getSchemas(handle);
            Multimap<String,String> schemasTables = getSchemasTables(handle, schemas);
            List<String> indexes = getIndexes(handle);
            DataBaseInfoBuilder builder = new DataBaseInfoBuilder()
                    .name(dbCredentials.getName())
                    .indexes(indexes)
                    .schemas(schemas)
                    .schemasTables(schemasTables);
            return builder.build();
        }
    }

    private List<String> getIndexes(Handle handle) {
        List<Map<String, Object>> list = handle.createQuery(Queries.SELECT_ALL_INDEXED).list();
        return Mapper.getIndexesMapper(list);
    }

    private List<String> getSchemas(Handle handle) {
        List<Map<String, Object>> list = handle.createQuery(Queries.SELECT_SCHEMAS).list();
        return Mapper.getSchemasMapper(list);
    }

    private Multimap<String,String> getSchemasTables(Handle handle, List<String> schemas) {
        Multimap<String,String> multimap = ArrayListMultimap.create();
        String schemasNames = Utils.quotaJoiner(schemas);
        String sql = Queries.SELECT_TABLES_FROM_SCHEMAS.replace(":table_schema", schemasNames);
        List<Map<String, Object>> tableSchema = handle.createQuery(sql)
                .list();
        for (Map<String, Object> map : tableSchema) {
            multimap.put(toStr(map.get("table_schema")), toStr(map.get("table_name")));
        }
        return multimap;
    }

}
