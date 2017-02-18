package database.diff.conf;

import database.diff.model.DbCredentials;
import org.postgresql.ds.PGSimpleDataSource;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * Created by tomek on 16.02.17.
 */
public class DataBaseConnection {

    private static final int TIMEOUT = 5;

    public static Optional<DataSource> getDataSource(DbCredentials dbCredentials) {
        try {
            PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
            pgSimpleDataSource.setPortNumber(dbCredentials.getPort());
            pgSimpleDataSource.setDatabaseName(dbCredentials.getName());
            pgSimpleDataSource.setUser(dbCredentials.getUser());
            pgSimpleDataSource.setPassword(dbCredentials.getPass());
            pgSimpleDataSource.setServerName(dbCredentials.getHost());
            pgSimpleDataSource.setLoginTimeout(TIMEOUT);
            pgSimpleDataSource.setSocketTimeout(TIMEOUT);
            return Optional.of(pgSimpleDataSource);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<DBI> getDBI(DbCredentials dbCredentials) {
        Optional<DataSource> dataSource = getDataSource(dbCredentials);
        if (dataSource.isPresent())
            return Optional.of(new DBI(dataSource.get()));
        return Optional.empty();
    }

}
