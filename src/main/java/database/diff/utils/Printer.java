package database.diff.utils;

import com.google.common.collect.FluentIterable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by tomek on 16.02.17.
 */
public class Printer {

    public static void printList(List<?> list) {
        FluentIterable.from(list)
                .forEach((Consumer<Object>) o -> System.out.println(o));
    }

}
