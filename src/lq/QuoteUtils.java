package lq;

import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class QuoteUtils {
    public static List<Quote> getQuotes() {
        PersistenceManager pm = PMF.get().getPersistenceManager(); 
        try {
            Query quoteQuery = pm.newQuery(Quote.class);
            quoteQuery.setFilter("approved == true");
            List<Quote> quotes = (List<Quote>) quoteQuery.execute();
            pm.retrieveAll(quotes);
            return quotes;
        }
        finally {
            pm.close();
        }
    }

    public static List<Quote> getQuotesPendingApproval() {
        PersistenceManager pm = PMF.get().getPersistenceManager(); 
        try {
            Query quoteQuery = pm.newQuery(Quote.class);
            quoteQuery.setFilter("approved == null");
            List<Quote> quotes = (List<Quote>) quoteQuery.execute();
            pm.retrieveAll(quotes);
            return quotes;
        }
        finally {
            pm.close();
        }
    }

    public static List<Quote> getQuotesOrderedByIdDesc() {
        List<Quote> quotes = getQuotes();
        Collections.sort(quotes,
                new Comparator<Quote>() {
                    public int compare(Quote q1, Quote q2) {
                        return Long.signum(q2.getId() - q1.getId());
                    }
        });
        return quotes;
    }

    public static List<Quote> getQuotesOrderedByScoreDesc() {
        List<Quote> quotes = getQuotes();
        Collections.sort(quotes,
                new Comparator<Quote>() {
                    public int compare(Quote q1, Quote q2) {
                        throw new RuntimeException("Score ordering not yet implemented");
                    }
        });
        return quotes;
    }

    public static List<Quote> getQuotesOrderedByDateDesc() {
        List<Quote> quotes = getQuotes();
        Collections.sort(quotes,
                new Comparator<Quote>() {
                    public int compare(Quote q1, Quote q2) {
                        return q2.getQuoteDate().compareTo(q1.getQuoteDate());
                    }
        });
        return quotes;
    }

    public static void approveQuote(Long id) {
        PersistenceManager pm = PMF.get().getPersistenceManager(); 
        try {
            Query quoteQuery = pm.newQuery(Quote.class);
            quoteQuery.setFilter("id == idParam");
            quoteQuery.declareParameters("Long idParam");
            List<Quote> quotes = (List<Quote>) quoteQuery.execute(id);

            for (Quote quote : quotes) {
                quote.setApproved(true);
                pm.makePersistent(quote);
            }
        }
        finally {
            pm.close();
        }
    }

    public static void rejectQuote(Long id) {
        PersistenceManager pm = PMF.get().getPersistenceManager(); 
        try {
            Query quoteQuery = pm.newQuery(Quote.class);
            quoteQuery.setFilter("id == idParam");
            quoteQuery.declareParameters("Long idParam");
            List<Quote> quotes = (List<Quote>) quoteQuery.execute(id);

            for (Quote quote : quotes) {
                quote.setApproved(false);
                pm.makePersistent(quote);
            }
        }
        finally {
            pm.close();
        }
    }
}
