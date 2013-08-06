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

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import rest.service.types.Action;
import rest.service.types.GamePlayer;

@Path("/player")
public class PlayerResource {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;
	
	private String id;
	
	private final Mapper mapper = new Mapper();
	
	public PlayerResource(){}
	
	public PlayerResource(final UriInfo uriInfo, final Request request, final String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GamePlayer getPlayer(){		
		Player player = find();
		return mapper.createPlayer(player);
	}
	
	private Player find(){
		Player p = GameEngine.instance.findPlayer(id);
		if(p==null){
			throw new IllegalArgumentException("player not found");
		}else{
			return p;
		}
	}
	
	
	@PUT
	public void addAction(final Action action){
		Player player = find();
		GameEngine.instance.perform(player, action);
	}
	
	@DELETE
	public void suicide(){
		Player player = find();
		GameEngine.instance.killAndRespawn(player);
	}
}
