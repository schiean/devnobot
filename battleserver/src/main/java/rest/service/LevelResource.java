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

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.service.types.GameObstacle;

@Path("/level")
public class LevelResource {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private final Mapper mapper = new Mapper();
	
	private static Cache<List<GameObstacle>> lvlCache = new Cache<List<GameObstacle>>(60000); 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<GameObstacle> getLevel() {
		synchronized (lvlCache) {
			if(lvlCache.needsUpdate()){
				List<GameObstacle> objs = new ArrayList<GameObstacle>();
				for (Node n : GameEngine.instance.getWallNodes()) {
					objs.add(mapper.createObstacle(n.getBoundsInParent()));
				}		
				lvlCache.set(objs);
			}
			return lvlCache.get();
			
		}
		
	}

}