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

public class App {

    private final static Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(final String[] args) throws Exception {

        String portNumberProperty = System.getProperty("devnobot.server.portNumber");
        String levelProperty = System.getProperty("devnobot.server.level");
        int portNumber = 7080;
        int level = 0;
        if (portNumberProperty != null) {
            try {
                portNumber = Integer.parseInt(portNumberProperty);
            } catch (NumberFormatException nfex) {
                LOG.severe("Port number must be numeric, will default to 7080");
                portNumber = 7080;
            }
        } else {
            LOG.info("Using default port number 7080");
        }
        if (levelProperty != null) {
            try {
                level = Integer.parseInt(levelProperty);
            } catch (NumberFormatException nfex) {
                LOG.severe("Level must be numeric, will default to 0");
                level = 0;
            }
            if (level > 2 || level < 0) {
                LOG.warning("Level is not 0, 1 or 2; will default to 0");
                level = 0;
            }
        } else {
            LOG.info("Using default playing level 0");
        }
        new GameServer(portNumber).start();
        GameEngine.main(args);
    }
}