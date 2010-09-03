package lq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class DateUtil {
    public static final SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat timestampFormat =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
    }
}
