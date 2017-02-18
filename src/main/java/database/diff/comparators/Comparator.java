package database.diff.comparators;

/**
 * Created by tomek on 18.02.17.
 */
public interface Comparator {

    String NEW_LINE = "\n";
    String DOUBLE_NEW_LINE = NEW_LINE + NEW_LINE;

    String getResult();
    String getInfo();
}
