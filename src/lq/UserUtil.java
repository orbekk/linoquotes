package lq;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    public static final String getAuthenticatedEmail() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
            return null;
        }
        else {
            if (hasZeroUsers() || userExists(user.getEmail())) {
              return user.getEmail();
            }
            else {
              return null;
            }
        }
    }

    public static final boolean isAuthenticated() {
        return getAuthenticatedEmail() != null;
    }

    public static final String getLoginUrl(String requestUrl) {
        UserService userService = UserServiceFactory.getUserService();
        return userService.createLoginURL(requestUrl);
    }

    public static final String getLogoutUrl(String requestUrl) {
        UserService userService = UserServiceFactory.getUserService();
        return userService.createLogoutURL(requestUrl);
    }

    private static final boolean hasZeroUsers() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Query query = pm.newQuery(Administrator.class);
            List<?> results = (List<?>) query.execute();
            return results.isEmpty();
        }
        finally {
            pm.close();
        }
    }

    public static final boolean userExists(String email) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Query query = pm.newQuery(Administrator.class);
            query.setFilter("email == emailParam");
            query.declareParameters("String emailParam");
            List<?> results = (List<?>) query.execute(email);
            return !results.isEmpty();
        }
        finally {
            pm.close();
        }

    }
}
