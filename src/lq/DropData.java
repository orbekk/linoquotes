package lq;

import java.io.IOException;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DropData extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            List<Quote> quotes = (List<Quote>) pm.newQuery(Quote.class).execute();
            for (Quote q : quotes) {
                pm.deletePersistent(q);
            }
            List<Vote> votes = (List<Vote>) pm.newQuery(Vote.class).execute();
            for (Vote v : votes) {
                pm.deletePersistent(v);
            }
        }
        finally {
            pm.close();
        }
    }
}
