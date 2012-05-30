/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package ch.zhaw.i11b.pwork.sem2.server;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import ch.zhaw.i11b.pwork.sem2.beans.config.JAXBContextResolver;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Messages;
import ch.zhaw.i11b.pwork.sem2.beans.Target;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.glassfish.grizzly.http.server.HttpServer;

import java.util.List;

import javax.ws.rs.core.MediaType;
/**
 *
 * @author elfixit
 */
public class MainTest {

    private HttpServer httpServer;

    private WebResource r;

    @Before
    public void setUp() throws Exception {
        httpServer = Main.startServer();
        Main.setupMessageHandlerFactory();
        Main.initMessageController();

        ClientConfig cc = new DefaultClientConfig();
        // use the following jaxb context resolver
        cc.getClasses().add(JAXBContextResolver.class);
        Client c = Client.create(cc);
        r = c.resource(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        httpServer.stop();
    }

    /**
     * Test checks that the application.wadl is reachable.
     */
    @Test
    public void testApplicationWadl() {
        String applicationWadl = r.path("application.wadl").get(String.class);
        assertTrue("Something wrong. Returned wadl length is not > 0",
                applicationWadl.length() > 0);
    }

    /**
     * Test check GET on the "flights" resource in "application/json" format.
     */
    @Test
    public void testGetMessagesInJSON() {
        // get the initial representation
        Messages messages = r.path("messages").
                accept("application/json").get(Messages.class);
        // check that there are two flight entries
        assertTrue("messages isnt instance of Messages",
                messages instanceof Messages);
        assertEquals("Messages does contain errors!!", 
        		0, messages.errors.size());
    }

    /**
     * Test checks PUT on the "flights" resource in "application/json" format.
     */
    @Test
    public void testPutMessageWhaitCheckFinished() {
        // get the initial representation
    	Message msg = new Message();
    	msg.from = "test@testing.com";
    	msg.message = "a test message";
    	msg.reminder = false;
    	msg.targets.add(new Target("Email", "testclient1@testing.com"));
    	msg.targets.add(new Target("Email", "testclient2@testing.com"));
    	
    	Message msg2 = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(Message.class, msg);
    	assertTrue("put Message didn't set a Message Id!", msg2.id != "");
    }
    
    /**
     * Test check messages if Message is open or finished
     */
    @Test
    public void testGetMessagesCheckIfNewIsOpenOrFinished() {
    	Messages msgs = r.path("messages").accept(MediaType.APPLICATION_JSON).get(Messages.class);
    	assertTrue("There isn't one Message finished or open!", msgs.open.size() > 0 | msgs.finished.size() > 0);
    }

}
