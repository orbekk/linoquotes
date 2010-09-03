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
            List<Quote> quotes = (List<Quote>) pm.newQuery(Quote.class).execute();
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
}
