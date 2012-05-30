package ch.zhaw.i11b.pwork.sem2.server;

import ch.zhaw.i11b.pwork.sem2.server.MessageApplication;
import ch.zhaw.i11b.pwork.sem2.server.controller.MessageController;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.EMailMessageHandler;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.MMSMessageHandler;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.PrintMessageHandler;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.SMSMessageHandler;
import ch.zhaw.i11b.pwork.sem2.server.resources.*;
import ch.zhaw.i11b.pwork.sem2.beans.*;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Main {

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static MessageController initMessageController() {
        MessageController controller = MessageController.Instance();
        return controller;
    }

    protected static void setupMessageHandlerFactory() {
        MessageHandlerFactory factory = MessageHandlerFactory.instance();
        factory.registerMessageHandler("Email", EMailMessageHandler.class);
        factory.registerMessageHandler("SMS", SMSMessageHandler.class);
        factory.registerMessageHandler("MMS", MMSMessageHandler.class);
        factory.registerMessageHandler("Print", PrintMessageHandler.class);
    }

    protected static HttpServer startServer() throws IOException {
        System.out.println("Starting Message Server...");
        MessageApplication ma = new MessageApplication();
        return GrizzlyServerFactory.createHttpServer(BASE_URI, ma);
    }

    public static void main(String[] args) throws IOException {
    	MessageController controller = initMessageController();
    	setupMessageHandlerFactory();
        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nTry out %smessages\nHit enter to stop it...",
                BASE_URI, BASE_URI));
        System.in.read();
        httpServer.stop();
    }
}
