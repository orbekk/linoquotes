package lq;

import java.io.Writer;
import java.io.PrintWriter;

public class Printer {
    private final PrintWriter out;
    
    public Printer(Writer out) {
        this.out = new PrintWriter(out);
    }

    public void printQuote(Quote quote) {
        printQuote(quote, null);
    }

    public String escapeDisplay(String content) {
        return Strings.escape(content)
                .replaceAll("\'", "&quot;")
                .replaceAll("(http://[^ \r\n]+)","<a href=\"$1\">$1</a>")
                .replaceAll("\n","<br>\n");

    }

    public String escapeRage(String content) {
        return Strings.escape(content).replaceAll("\'", "&quot;");
    }

    public void printQuote(Quote quote, Long displayIndex) {
        if (displayIndex == null) {
            displayIndex = quote.getId();
        }
        out.println("<br>");
        out.print("<a href=\"/view_quote?id=" + quote.getId() + "\">" +
            "#" + displayIndex +
            "</a>"+
            ", lagt til av " + Strings.escape(quote.getAuthor()));
        out.println("<br>");
        String date = DateUtil.dateFormat.format(quote.getQuoteDate());
        out.println("Dato: " + date + ", Score: ");
        out.println("<span id=\"v" + quote.getId() + "\">");
        out.print(QuoteUtil.formatScore(quote));
        out.println(", Vote: <font size=\"-1\">");
        for(int nv=1; nv<=5; nv++) 
            out.print(" <a href=\"javascript:vote(" + quote.getId() + ","+nv+")\">"+nv+"</a>");
        out.print("</font></span>");
        out.print(", ");
        printRageButton(quote);
        out.println("<br>");
        out.println("<br> <br>");
        out.println();
        out.println(escapeDisplay(quote.getContent()));
        out.println("");
        out.println("<hr>");
    }

    public void printRageButton(Quote quote) {
        out.print(
                "<form method=\"post\" style=\"display: inline;\"" +
                "action=\"http://www.vidarholen.net/contents/rage/index.php\">" +
                "<input type=\"hidden\" name=\"irc\" value=\"");
        out.print(escapeRage(quote.getContent()));
        out.print("\"/>");
        out.print("<input type=\"submit\" class=\"ragebutton\" value=\"Rage it\"/></form>");
    }
}
