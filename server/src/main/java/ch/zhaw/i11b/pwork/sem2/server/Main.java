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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Main {

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    public static final URI BASE_URI = getBaseURI();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * @return
     */
    protected static MessageController initMessageController() {
    	logger.debug("First initialisation of the MessageController, just for that that it is done.. ;-)");
        MessageController controller = MessageController.Instance();
        return controller;
    }

    /**
     * Erste initialisierung der MessageHandlerFactory. 
     * registriert die MessageHandlers:
     *   * Email: EMailMessageHandler
     *   * SMS: SMSMessageHandler
     *   * MMS: MMSMessageHandler
     *   * Print: PrintMessageHandler
     */
    protected static void setupMessageHandlerFactory() {
    	logger.debug("Setup MessageHandlerFactory with the classes");
        MessageHandlerFactory factory = MessageHandlerFactory.instance();
        factory.registerMessageHandler("Email", EMailMessageHandler.class);
        factory.registerMessageHandler("SMS", SMSMessageHandler.class);
        factory.registerMessageHandler("MMS", MMSMessageHandler.class);
        factory.registerMessageHandler("Print", PrintMessageHandler.class);
    }

    /**
     * Code for create the HttpServer
     * @return
     * @throws IOException
     */
    protected static HttpServer startServer() throws IOException {
        logger.info("Starting Http Message Server...");
        MessageApplication ma = new MessageApplication();
        return GrizzlyServerFactory.createHttpServer(BASE_URI, ma);
    }

    /**
     * The normal main method.. starts the server.. and makes some blabla..
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	logger.info("PWorkSem2 Server starting");
    	initMessageController();
    	setupMessageHandlerFactory();
        HttpServer httpServer = startServer();
        logger.info("Jersey app started with WADL available at {}application.wadl", BASE_URI);
        logger.info("Try out {}messages", BASE_URI);
        logger.info("Hit enter to stop it...");
        System.in.read();
        httpServer.stop();
    }
}
