package codecool.java.handler;

import codecool.java.dao.DbAuthorizationDAO;
import codecool.java.model.User;
import com.sun.net.httpserver.HttpExchange;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CookieHelper {

    private static final String SESSION_COOKIE_NAME = "sessionId";
    private static final int EXPIRATION_COOKIE_TIME = 900;

    public List<HttpCookie> parseCookies(String cookieString){
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieString == null || cookieString.isEmpty()){ // what happens if cookieString = null?
            return cookies;
        }
        for(String cookie : cookieString.split(";")){
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1, cookie.length());
            HttpCookie httpCookie = new HttpCookie(cookieName, cookieValue.replace("\"",""));
            httpCookie.setMaxAge(EXPIRATION_COOKIE_TIME);
            cookies.add(httpCookie);
        }
        return cookies;
    }

    public Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies){
        for(HttpCookie cookie : cookies){
            if(cookie.getName().equals(name))
                return Optional.ofNullable(cookie);
        }
        return Optional.empty();
    }

    public boolean isCookiePresent(HttpExchange httpExchange) {
        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            return !cookie.get().hasExpired();
        }
        return false;
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        return findCookieByName(SESSION_COOKIE_NAME , cookies);
    }

    public void createNewCookie(HttpExchange httpExchange, User user) throws SQLException, ClassNotFoundException, ParseException {
        DbAuthorizationDAO authorizationDAO = new DbAuthorizationDAO();
        UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        Optional<HttpCookie> cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
        cookie.get().setMaxAge(EXPIRATION_COOKIE_TIME);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString()+";Max-Age=" + EXPIRATION_COOKIE_TIME + ";");
        authorizationDAO.saveCookie(user.getId(), cookie);
    }

    public void refreshCookie(HttpExchange httpExchange) {
        try {
            DbAuthorizationDAO authorizationDAO = new DbAuthorizationDAO();
            Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);
            authorizationDAO.refreshCookie(cookie);
            authorizationDAO.disableAllOutdatedCookies();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsertypeValid(HttpExchange httpExchange, int userType) {
        // TODO get usertype from db, compare to needed
        return true;
    }
}
