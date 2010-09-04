package lq;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
    // hacks....
    private HashMap<Long, Quote> quoteCache;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        quoteCache = new HashMap<Long, Quote>();
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
            List<Vote> votes = importVotes(voteRows);

            PersistenceManager pm = PMF.get().getPersistenceManager();

            try {
                String importIdString = req.getParameter("importId");
                if (importIdString ==  null) {
                    uploadInChunks(pm, votes, 100);
                    uploadInChunks(pm, quotes, 100);
                    resp.getWriter().println("Import finished.");
                }
                else {
                    int importId = Integer.parseInt(importIdString);
                    Quote q = quotes.get(importId);
                    pm.makePersistent(q);
                    for (Vote v : votes) {
                        if (v.getQuoteId() == q.getId()) {
                            pm.makePersistent(v);
                        }
                    }
                    resp.getWriter().println("There are " + quotes.size() +
                            " quotes in the old db.");
                    resp.getWriter().println("Quote " + importId + " imported.");
                }
            }
            finally {
                pm.close();
            }
        } catch (Exception e) {
            e.printStackTrace(resp.getWriter());
        }
    }

    private void uploadInChunks(PersistenceManager pm, List<?> objects, int chunkSize) {
        for (int i = 0; i < objects.size(); i += chunkSize) {
            List<Object> chunk = new ArrayList<Object>();
            for (int j = i; j < Math.min(objects.size(), i + chunkSize); j++) {
                chunk.add(objects.get(j));
            }
            pm.makePersistentAll(chunk);
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
        Long oldId = Long.parseLong(getChildWithName("id", quoteRow));

        Long id = Long.parseLong(getChildWithName("number", quoteRow));

        String approvedString = getChildWithName("approved", quoteRow);
        Boolean approved = approvedString.equals("1") ? true : false; 

        String name = getChildWithName("name", quoteRow);

        String dateString = getChildWithName("date", quoteRow);
        Date date = DateUtil.dateFormat.parse(dateString);

        String content = getChildWithName("text", quoteRow);

        String timeString = getChildWithName("time", quoteRow);
        Date timestamp = DateUtil.timestampFormat.parse(timeString);

        String ip = getChildWithName("ip", quoteRow);

        Quote quote = new Quote(date, name, content, ip);
        quote.setId(id);
        quote.setTimestamp(timestamp);
        quote.setApproved(approved);

        quoteCache.put(oldId, quote);
        return quote;
    }

    private List<Vote> importVotes(List voteRows) throws Exception {
        List<Vote> votes = new ArrayList<Vote>();
        for (Object row : voteRows) {
            @SuppressWarnings("unchecked")
            Element elem = (Element) row;
            Vote vote = importVote(elem);
            if (vote != null) {
                votes.add(vote);
            }
        }
        logger.info("Imported " + votes.size() + " votes");
        return votes;
    }

    private Vote importVote(Element voteRow) throws Exception {
        Long quoteId = Long.parseLong(getChildWithName("id", voteRow));
        Quote quote = quoteCache.get(quoteId);
        if (quote == null) {
            logger.warning("Could not find quote with id " + quoteId);
            return null;
        }
        Long rating = Long.parseLong(getChildWithName("vote", voteRow));
        Date timestamp = lq.DateUtil.timestampFormat.parse(getChildWithName("time", voteRow));
        String ip = getChildWithName("ip", voteRow);

        Vote vote = new Vote(quoteId, rating, ip);
        vote.setTimestamp(timestamp);
        QuoteUtil.addVote(quote, vote);
        return vote;
    }

    private String getChildWithName(String name, Element parent) throws Exception {
        String xpath = "field[@name='" + name + "']";
        Element child = (Element) XPath.selectSingleNode(parent, xpath);
        return child.getValue();
    }
}

