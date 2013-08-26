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

import game.GameEngine;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import rest.service.types.GameObstacle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * RESTful endpoints for URLs starting with <Context>/level/.
 *
 * @author Arjen van Schie
 */
@Path("/level")
public class LevelResource {

    private static final Logger LOGGER = Logger.getLogger(LevelResource.class.getName());

    private final Mapper mapper = new Mapper();

    private static final int CACHE_SIZE = 60000;
    private static Cache<List<GameObstacle>> lvlCache = new Cache<>(CACHE_SIZE);

    /**
     * RESTful endpoint for HTTP GET URL <Context>/level.
     *
     * @return List<GameObstacle>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GameObstacle> getLevel() {
        synchronized (lvlCache) {
            if (lvlCache.needsUpdate()) {
                List<GameObstacle> obstacles = new ArrayList<GameObstacle>();
                for (Node n : GameEngine.instance.getWallNodes()) {
                    if (n instanceof Rectangle) {
                        LOGGER.info("Ignoring Rectangle object from list of Nodes");
                    } else {
                        obstacles.add(mapper.createObstacle(n.getBoundsInParent()));
                    }
                }
                lvlCache.set(obstacles);
            }
            return lvlCache.get();

        }

    }

}