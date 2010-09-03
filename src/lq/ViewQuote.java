package lq;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewQuote extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String idParam = req.getParameter("id");
        Long id = Long.parseLong(idParam);
        Quote quote = QuoteUtil.getQuoteWithId(id);

        resp.setContentType("text/html");

        if (quote != null) {
            Printer printer = new Printer(resp.getWriter());
            printer.printQuote(quote);
        }
        else {
            resp.getWriter().println("Quote not found."); 
        }
    }
}
