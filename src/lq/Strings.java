package lq;

public class Strings {
    public static final String escape(String raw) {
        return raw
            .replaceAll("&","&amp;")
            .replaceAll("<","&lt;")
            .replaceAll(">","&gt;")
            .replaceAll("  ","&nbsp;&nbsp;");

    }
}
