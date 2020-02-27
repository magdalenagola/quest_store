package codecool.java.handler;

import codecool.java.model.User;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CookieHelper {

    public List<HttpCookie> parseCookies(String cookieString){
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieString == null || cookieString.isEmpty()){ // what happens if cookieString = null?
            return cookies;
        }
        for(String cookie : cookieString.split(";")){
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1, cookie.length());
            cookies.add(new HttpCookie(cookieName, cookieValue.replace("\"","")));
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
    public  Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        return findCookieByName("UserID", cookies);
    }

    public void createNewCookie(HttpExchange httpExchange, User user){
        UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        Optional<HttpCookie> cookie = Optional.of(new HttpCookie("UserID", String.valueOf(user.getId())));
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString()+";Max-Age=3600;");
    }
}
