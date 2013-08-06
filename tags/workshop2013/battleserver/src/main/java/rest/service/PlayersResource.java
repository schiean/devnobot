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
import game.visual.types.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.service.types.GamePlayer;

@Path("/players")
public class PlayersResource {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;
	
	private final Mapper mapper = new Mapper();
	
	private static Cache<List<GamePlayer>> playerCache = new Cache<List<GamePlayer>>(500); 	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void newPlayer(final GamePlayer gamePlayer) throws IOException {
		// doesn;t copy the kill/dead rate obviously
		Player player = new Player(gamePlayer.getName(), Color.web(gamePlayer.getColor()), gamePlayer.getId());		
		GameEngine.instance.register(player);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<GamePlayer> getPlayers(){
		synchronized (playerCache) {
			if(playerCache.needsUpdate()){
				List<GamePlayer> players = new ArrayList<GamePlayer>();
				for(Player p :GameEngine.instance.getPlayers()){
					players.add(mapper.createPlayer(p));
				}
				playerCache.set(players);
			}
			return playerCache.get();
		}		
	}
	

	// Allows to type http://localhost:7080/players/autobot
	@Path("{player}")
	public PlayerResource getPlayer(@PathParam("player") final String id) {
		return new PlayerResource(uriInfo, request, id);
	}
}
