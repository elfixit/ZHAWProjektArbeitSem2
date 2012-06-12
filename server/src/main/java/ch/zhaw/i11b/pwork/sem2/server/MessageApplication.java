package ch.zhaw.i11b.pwork.sem2.server;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application for javax.ws.rs
 * makes shure that JAXBContextResolver and MessagingService gets scanned.
 * @author oups
 *
 */
@ApplicationPath("resources")
public class MessageApplication extends PackagesResourceConfig {
	private Logger log;
	
	/**
	 * 
	 */
	public MessageApplication() {
		super("ch.zhaw.i11b.pwork.sem2");
		log = LoggerFactory.getLogger(MessageApplication.class);
		log.info("creating Messaging application");
	}

}
