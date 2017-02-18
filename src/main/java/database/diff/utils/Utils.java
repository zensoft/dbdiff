package database.diff.utils;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Function;
import database.diff.comparators.AbstractComparator;
import database.diff.comparators.SchemasComparator;
import database.diff.comparators.TablesComparator;
import database.diff.conf.DataBaseConnection;
import database.diff.managers.DataBaseInfoManager;
import database.diff.model.DataBaseInfo;
import database.diff.model.DatabasesInfo;
import database.diff.model.DbCredentials;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.skife.jdbi.v2.DBI;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static database.diff.model.DbCredentials.*;

/**
 * Created by tomek on 16.02.17.
 */
public class Utils {

    public static String quotaJoiner(List<String> list) {
        return list.stream()
                .map((Function<String, String>) s -> "'" + s + "'").collect(Collectors.joining(","));
    }

    public static String toStr(Object o) {
        return String.valueOf(o);
    }

    private static DbCredentials createFromPropertiesFile(File file, String dbPrefix) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            return new Builder()
                    .name(properties.getProperty(dbPrefix + ".name"))
                    .user(properties.getProperty(dbPrefix + ".user"))
                    .pass(properties.getProperty(dbPrefix + ".pass"))
                    .host(properties.getProperty(dbPrefix + ".host"))
                    .port(Integer.parseInt(properties.getProperty(dbPrefix + ".port")))
                    .build();
        } catch (Exception e){
            return new Builder().build();
        }
    }

    public static DatabasesInfo getDatabasesInfo(File fileConfig, boolean reverseDatabases) {
        Observable<DataBaseInfo> infoObservable = createFirstDatabaseObservable(fileConfig);
        Observable<DataBaseInfo> infoObservable2 = createSecondDatabaseObservable(fileConfig);

        Observable<DatabasesInfo> zip = Observable.zip(infoObservable, infoObservable2, (firstDb, secondDb) ->
                reverseDatabases ? new DatabasesInfo(secondDb, firstDb) : new DatabasesInfo(firstDb, secondDb));
        return zip.blockingFirst();
    }

    public static DbCredentials createFirstDbCredentials(File file) {
        return createFromPropertiesFile(file, "db1");
    }

    public static DbCredentials createSecondDbCredentials(File file) {
        return createFromPropertiesFile(file, "db2");
    }

    public static Observable<DataBaseInfo> createFirstDatabaseObservable(File fileConfig) {
        return createObservable(createFirstDatabaseInfo(fileConfig));
    }

    public static Observable<DataBaseInfo> createSecondDatabaseObservable(File fileConfig) {
        return createObservable(createSecondDatabaseInfo(fileConfig));
    }

    public static Observable<DataBaseInfo> createObservable(DataBaseInfo dataBaseInfo) {
        return Observable.fromCallable(() -> dataBaseInfo).subscribeOn(Schedulers.newThread());
    }

    public static DataBaseInfo createFirstDatabaseInfo(File fileConfig) {
        return createDatabaseInfo(Utils.createFirstDbCredentials(fileConfig));
    }

    public static DataBaseInfo createSecondDatabaseInfo(File fileConfig) {
        return createDatabaseInfo(Utils.createSecondDbCredentials(fileConfig));
    }

    public static DataBaseInfo createDatabaseInfo(DbCredentials dbCredentials) {
        Optional<DBI> dbiOptional = DataBaseConnection.getDBI(dbCredentials);
        DataBaseInfoManager manager = new DataBaseInfoManager(dbiOptional.get(), dbCredentials);
        try {
            return manager.createDataBaseInfo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new DataBaseInfo.DataBaseInfoBuilder().build();
        }
    }

    public static List<AbstractComparator> comparators(DatabasesInfo databasesInfo) {
        return Lists.newArrayList(
                new SchemasComparator(databasesInfo),
                new TablesComparator(databasesInfo)
        );
    }

}
