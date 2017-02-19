package database.diff;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.FileConverter;
import database.diff.comparators.DatabasesComparator;
import database.diff.model.DatabasesInfo;
import database.diff.utils.Utils;
import database.diff.validators.FileParamValidator;

import java.io.File;

public class App
{
    private static final String appName = "java -jar db-diff.jar";

    private JCommander commander;

    public App() {
        this.commander = new JCommander();
    }

    @Parameter(names = {"-conf"},
            description = "Configuration file.",
            converter = FileConverter.class,
            validateWith = FileParamValidator.class,
            required = true)
    private File fileConfig;

    @Parameter(names = {"-reverse"}, required = false, description = "Reverse databases.")
    private boolean reverseDatabases;

    public void run() {
        DatabasesInfo databasesInfo = Utils.getDatabasesInfo(fileConfig, reverseDatabases);
        DatabasesComparator comparator = new DatabasesComparator(databasesInfo, Utils.comparators(databasesInfo));
        comparator.printResult();
    }

    public void parseArgs(String[] args) {
        args = new String[] {"-conf", "/home/tomek/db.prop"};
        try {
            commander.setProgramName(appName);
            commander.addObject(this);
            commander.parse(args);
            run();
        } catch (ParameterException pe) {
            printParameterException(pe);
        }
    }

    private void printParameterException(ParameterException pe) {
        StringBuilder stringBuilder = new StringBuilder();
        commander.usage(stringBuilder);
        stringBuilder.append("\n")
                .append("Error:\n")
                .append(pe.getMessage());
        System.out.println(stringBuilder);
    }

    public static void main(String[] args) {
        new App().parseArgs(args);
    }

}
