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
package devnobots;

import game.GameEngine;
import rest.service.GameServer;

import java.util.logging.Logger;

/**
 * Devnobot server main class.
 *
 * @author Arjen van Schie
 */
public final class App {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    /**
     * Default port number.
     */
    private static final int DEFAULT_PORT_NUMBER = 7080;

    /**
     * Private constructor since this class only has static methods.
     */
    private App() {

    }

    /**
     * .
     *
     * @param args -
     */
    public static void main(final String[] args) {

        String portNumberProperty = System.getProperty("devnobot.server.portNumber");
        String levelProperty = System.getProperty("devnobot.server.level");
        int portNumber = DEFAULT_PORT_NUMBER;
        if (portNumberProperty != null) {
            try {
                portNumber = Integer.parseInt(portNumberProperty);
            } catch (NumberFormatException nfex) {
                LOG.severe("Port number must be numeric, will default to 7080");
                portNumber = DEFAULT_PORT_NUMBER;
            }
        } else {
            LOG.info("Using default port number 7080");
        }
        if (levelProperty != null) {
            try {
                int level = Integer.parseInt(levelProperty);
                if (level > 2 || level < 0) {
                    LOG.warning("Level is not 0, 1 or 2; will default to 0");
                }
            } catch (NumberFormatException nfex) {
                LOG.severe("Level must be numeric, will default to 0");
            }
        } else {
            LOG.info("Using default playing level 0");
        }
        new GameServer(portNumber).start();
        GameEngine.main(args);
    }
}