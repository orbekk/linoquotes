package lq;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddVote extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        resp.setContentType("text/html");

        Long quoteId;
        Long rating;
        String ip;

        try {
            quoteId = Long.parseLong(req.getParameter("id"));
            rating = Long.parseLong(req.getParameter("vote"));
            ip = req.getRemoteAddr();

            String forward = req.getHeader("X-Forwarded-For");
            if (!Strings.nullOrEmpty(forward)) {
                ip = ip + " (" + forward + ")";
            }
        } catch (NumberFormatException e) {
            resp.getWriter().println("invalid arguments");
            return;
        }

        if (rating.longValue() < 0 || rating.longValue() > 5) {
            resp.getWriter().println("nice try");
            return;
        }

        Quote quote = QuoteUtil.getQuoteWithId(quoteId);
        if (quote == null) {
            resp.getWriter().println("failed to retrieve quote");
            return;
        }

        Vote vote = new Vote(quoteId, rating, ip);
        QuoteUtil.addVote(quote, vote);

        PersistenceManager pm = PMF.get().getPersistenceManager();
        // Transaction tx = pm.currentTransaction();
        try {
            // tx.begin();
            pm.makePersistent(quote);
            pm.makePersistent(vote);
            // tx.commit();
        } finally {
            // if (tx.isActive()) {
            //     tx.rollback();
            // }
            pm.close();
        }

        resp.getWriter().println(QuoteUtil.formatScore(quote));
    }
}
