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

}
