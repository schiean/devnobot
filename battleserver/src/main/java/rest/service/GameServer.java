/**
 * Copyright 2011 AJG van Schie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package rest.service;


import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import java.util.logging.Logger;

/**
 * This class starts a Jetty server.
 *
 * @author Arjen van Schie
 */
public class GameServer {

    /**
     * Port number to listen on.
     */
    private final int port;
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

    /**
     * Specialised constructor.
     *
     * @param port -
     */
    public GameServer(final int port) {
        this.port = port;
    }

    /**
     * Starts a Jetty server instance.
     */
    public void start() {
        ServletHolder sh = new ServletHolder(ServletContainer.class);
        sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        sh.setInitParameter("com.sun.jersey.config.property.packages", "rest.service");
        Server server = new Server(port);


        final Context context = new Context(server, "/", Context.SESSIONS);
        context.addServlet(sh, "/*");
        try {
            server.start();
        } catch (Exception ex) {
            LOGGER.severe("Unable to start the Jetty server");
            throw new IllegalStateException("Unable to start the Jetty server", ex);
        }
    }
}
