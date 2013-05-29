package ru.hantim.phonebook.rest.integration;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.hantim.phonebook.domain.IUser;
import ru.hantim.phonebook.domain.impl.User;

public class BaseControllerITest {
    private static int port = 7900;
    private static String contextPath = "/phonebook";
    private static Server server;

    protected static IUser expectedUser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        User.Builder builder = new User.Builder();
        expectedUser = builder.newUser(1l).
                setLogin("login").setPhone("phone").
                build();


        startJetty();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        stopJetty();
    }

    private static void startJetty() throws Exception {
        server = new Server();

        SocketConnector connector = new SocketConnector();
        connector.setPort(port);

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath(contextPath);
        context.setWar("src/main/webapp");

        server.setConnectors(new Connector[]{connector});
        server.setHandler(context);
        server.start();
    }

    private static void stopJetty() throws Exception {
        server.stop();
    }

    protected static String getRestEndpoint(String path) {
        return String.format("http://localhost:%1$d%2$s/rest/%3$s", port, contextPath, path);
    }

    protected static String getRestEndpoint(String path, String param) {
        return String.format("http://localhost:%1$d%2$s/rest/%3$s/%4$s", port, contextPath, path, param);
    }

    protected static String getRestEndpoint(String path, String pathParam, String requestParam) {
        return String.format("http://localhost:%1$d%2$s/rest/%3$s/%4$s%5$s", port, contextPath, path, pathParam, requestParam);
    }
}
