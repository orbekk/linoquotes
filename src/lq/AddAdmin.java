package lq;

import java.io.IOException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html");
        String email = req.getParameter("email");
        if (UserUtil.isAuthenticated() || UserUtil.hasZeroUsers()) {
            if (addUser(resp, email)) {
                resp.getWriter().println(Strings.escape(email) + " kan nå moderere quotes.");
            }

            String logoutUrl = UserUtil.getLogoutUrl(req.getRequestURI());
            resp.getWriter().println("<a href=\"" + logoutUrl + "\">Logg ut</a><br>");
        }
        else {
            String loginUrl = UserUtil.getLoginUrl(req.getRequestURI());
            resp.getWriter().println("<a href=\"" + loginUrl + "\">Logg inn</a><br>");
        }
    }

    private boolean addUser(HttpServletResponse resp, String email) 
            throws IOException {
        if (Strings.nullOrEmpty(email)) {
            resp.getWriter().println("Feilkode µ (spesifiser email).<br>");
            return false;
        }
        else {
            PersistenceManager pm = PMF.get().getPersistenceManager();
            try {
                if (!UserUtil.userExists(email)) {
                    Administrator newAdmin = new Administrator(email);
                    pm.makePersistent(newAdmin);
                    return true;
                }
                else {
                    resp.getWriter().println("Brukeren " + Strings.escape(email) +
                            " finnes allerede<br>");
                    return false;
                }
            }
            finally {
                pm.close();
            }
        }
    }
}
