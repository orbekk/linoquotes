package lq;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Redirect extends HttpServlet {
    private String destination;

    @Override
    public void init(ServletConfig config) throws ServletException {
        destination = config.getInitParameter("destination");    
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        resp.sendRedirect(destination);
    }
}
