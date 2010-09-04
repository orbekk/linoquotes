package lq;

public class Doubles {
    public static final int signum(Double d) {
        if (d < 0.0) {
            return -1;
        }
        else if (d > 0.0) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
