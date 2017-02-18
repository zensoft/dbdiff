package database.diff.model;

import com.google.common.collect.Multimap;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by tomek on 16.02.17.
 */
public class DataBaseInfo {

    private String name;
    private List<String> schemas;
    private Multimap<String, String> schemasTables;

    private DataBaseInfo(DataBaseInfoBuilder builder){
        this.name = builder.name;
        this.schemas = builder.schemas;
        this.schemasTables = builder.schemasTables;
    }

    public String getName() {
        return name;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public Multimap<String, String> getSchemasTables() {
        return schemasTables;
    }

    public static class DataBaseInfoBuilder {

        private String name;
        private List<String> schemas;
        private Multimap<String, String> schemasTables;

        public DataBaseInfoBuilder schemas(List<String> schemas) {
            this.schemas = schemas;
            return this;
        }

        public DataBaseInfoBuilder schemasTables(Multimap schemasTables) {
            this.schemasTables = schemasTables;
            return this;
        }

        public DataBaseInfoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DataBaseInfo build() {
            return new DataBaseInfo(this);
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
