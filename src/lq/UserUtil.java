package lq;

import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserUtil {
    public static final String getAuthenticatedEmail() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
            return null;
        }
        else {
            return user.getEmail();
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
}
