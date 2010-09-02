package lq;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ImportQuotes extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        String path = "db-dump.xml";

        resp.setContentType("text/plain");
        resp.getWriter().println("Trying to import quotes from " + path);

        SAXBuilder builder = new SAXBuilder();

        try {
            builder.build(path);
            resp.getWriter().println("Parsed successfully.");
        } catch (Exception e) {
            resp.getWriter().println("Failed to parse xml: " + e.toString());
        }
    }
}

