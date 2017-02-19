package database.diff.constant;

/**
 * Created by tomek on 16.02.17.
 */
public class Queries {

    public static final String SELECT_SCHEMAS = "SELECT table_schema " +
            "FROM information_schema.tables " +
            "WHERE table_schema NOT IN ('pg_catalog','information_schema') " +
            "GROUP BY table_schema " +
            "ORDER BY table_schema;";

    public static final String SELECT_TABLES_FROM_SCHEMAS = "SELECT table_schema,table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema NOT IN ('pg_catalog','information_schema') " +
            "AND table_schema  IN (:table_schema) " +
            "ORDER BY table_schema,table_name;";


    public static final String SELECT_ALL_INDEXED = "select" +
            " t.relname as table_name," +
            " i.relname as index_name," +
            " a.attname as column_name " +
            "from" +
            " pg_class t," +
            " pg_class i," +
            " pg_index ix," +
            " pg_attribute a " +
            "where" +
            " t.oid = ix.indrelid" +
            " and i.oid = ix.indexrelid" +
            " and a.attrelid = t.oid" +
            " and a.attnum = ANY(ix.indkey)" +
            " and t.relkind = 'r'" +
            " and t.relname not like 'pg_%' " +
            "order by" +
            " t.relname," +
            " i.relname;";

}
