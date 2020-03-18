package codecool.java.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class StaticHandlerTest {

    StaticHandler staticHandler = spy(new StaticHandler());
    HttpExchange httpExchange = mock(HttpExchange.class);

    @Test
    void shouldSendFile() throws IOException, URISyntaxException {
        URI uri = new URI("/test.txt");
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("test.txt");
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        when(httpExchange.getRequestURI()).thenReturn(uri);
        staticHandler.handle(httpExchange);
        verify(staticHandler).sendFile(httpExchange, url);
    }

    @Test
    void shouldSend404() throws IOException, URISyntaxException {
        URI uri = new URI("/test2.txt");
        when(httpExchange.getRequestURI()).thenReturn(uri);
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        staticHandler.handle(httpExchange);
        verify(staticHandler).send404(httpExchange);
    }

}
