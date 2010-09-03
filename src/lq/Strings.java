package lq;

public class Strings {
    public static final String escape(String raw) {
        return raw
            .replaceAll("&","&amp;")
            .replaceAll("<","&lt;")
            .replaceAll(">","&gt;")
            .replaceAll("  ","&nbsp;&nbsp;");
    }

    public static final boolean nullOrEmpty(String str) {
      return str == null || str.isEmpty();
    }
}
