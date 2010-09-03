package lq;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jdo.PersistenceManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ImportQuotes extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ImportQuotes.class.getName());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timestampFormat =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        String path = "db-dump.xml";

        resp.setContentType("text/plain");
        resp.getWriter().println("Trying to import quotes from " + path);

        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = builder.build(path);
                Element root = document.getRootElement();
            List quoteRows = XPath.selectNodes(root,
                    "//table_data[@name='quotes']/*");
            List voteRows = XPath.selectNodes(root,
                    "//table_data[@name='votes']/*");

            List<Quote> quotes = importQuotes(quoteRows);
            List<Vote> votes = importVotes(quotes, voteRows);

            PersistenceManager pm = PMF.get().getPersistenceManager();

            try {
                pm.makePersistentAll(quotes); 
                pm.makePersistentAll(votes);
            }
            finally {
                pm.close();
            }
        } catch (Exception e) {
            resp.getWriter().println("Failed to parse xml: " + e.toString());
            e.printStackTrace();
        }
    }

    private List<Quote> importQuotes(List quoteRows) throws Exception {
        List<Quote> quotes = new ArrayList<Quote>();
        for (Object row : quoteRows) {
            @SuppressWarnings("unchecked")
            Element elem = (Element) row;
            quotes.add(importQuote(elem));
        }
        logger.info("Imported " + quotes.size() + " quotes");
        return quotes;
    }

    private Quote importQuote(Element quoteRow) throws Exception {
        Long id = Long.parseLong(getChildWithName("id", quoteRow));

        String approvedString = getChildWithName("approved", quoteRow);
        Boolean approved = approvedString.equals("1") ? true : false; 

        String name = getChildWithName("name", quoteRow);

        String dateString = getChildWithName("date", quoteRow);
        Date date = dateFormat.parse(dateString);

        String content = getChildWithName("text", quoteRow);

        String timeString = getChildWithName("time", quoteRow);
        Date timestamp = timestampFormat.parse(timeString);

        String ip = getChildWithName("ip", quoteRow);

        Quote quote = new Quote(date, name, content, ip);
        quote.setId(id);
        quote.setTimestamp(timestamp);
        quote.setApproved(approved);
        return quote;
    }

    private List<Vote> importVotes(List<Quote> quotes, List voteRows) throws Exception {
        List<Vote> votes = new ArrayList<Vote>();
        for (Object row : voteRows) {
            @SuppressWarnings("unchecked")
            Element elem = (Element) row;
            Vote vote = importVote(elem, quotes);
            if (vote != null) {
                votes.add(vote);
            }
        }
        logger.info("Imported " + votes.size() + " votes");
        return votes;
    }

    private Vote importVote(Element voteRow, List<Quote> quotes) throws Exception {
        Long quoteId = Long.parseLong(getChildWithName("id", voteRow));
        Quote quote = getQuoteWithId(quotes, quoteId);
        if (quote == null) {
            logger.warning("Could not find quote with id " + quoteId);
            return null;
        }
        Long rating = Long.parseLong(getChildWithName("vote", voteRow));
        Date timestamp = timestampFormat.parse(getChildWithName("time", voteRow));
        String ip = getChildWithName("ip", voteRow);

        Vote vote = new Vote(rating, ip); 
        vote.setTimestamp(timestamp);
        quote.getVotes().add(vote);
        return vote;
    }

    private String getChildWithName(String name, Element parent) throws Exception {
        String xpath = "field[@name='" + name + "']";
        Element child = (Element) XPath.selectSingleNode(parent, xpath);
        return child.getValue();
    }

    private Quote getQuoteWithId(List<Quote> quotes, Long id) {
        for (Quote quote : quotes) {
            if (quote.getId().equals(id)) {
                return quote;
            }
        }
        throw new RuntimeException("Could not find quote.");
    }
}

