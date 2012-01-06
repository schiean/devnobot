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

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import rest.service.types.Action;
import rest.service.types.GameObstacle;
import rest.service.types.GamePlayer;
import rest.service.types.World;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class ClientApi {
	
	private final String hostUrl;
	private final WebResource service;

	/**
	 * Read this once for each level
	 * @return
	 */
	public List<GameObstacle> readLevel(){
		return Arrays.asList(service.path("level").accept(MediaType.APPLICATION_JSON).get(GameObstacle[].class));
	}
	
	/**
	 * Read this in your game loop to see what's going on
	 * @return
	 */
	public World readWorldStatus(){		
		return service.path("world").accept(MediaType.APPLICATION_JSON).get(World.class); 
	}
	
	/**
	 * Read this every now and then if you want to know the player status updates
	 * @return
	 */
	public List<GamePlayer> readPlayers(){
		return Arrays.asList(service.path("players").accept(MediaType.APPLICATION_JSON).get(GamePlayer[].class));			
	}
		
	/**
	 * Call this once to create your player
	 * @param name
	 * @param webcolor
	 * @param id		something like asd54tygasd45rtfgads5
	 * @return
	 */
	public boolean createPlayer(final String name, final String webcolor, final String id){
		GamePlayer player = new GamePlayer();
		player.setColor(webcolor);
		player.setName(name);		
		player.setId(id); // to prevent others from accidentally stealing your bot
		ClientResponse response = service.path("players").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, player);
		return response.getStatus() == 204;
	}
	
	/**
	 * Add action for player with id to its queue
	 * @param action
	 * @param id
	 */
	public boolean addAction(final Action action, final String id){
		ClientResponse response = service.path("players").path(id).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, action);
		return response.getStatus() == 204;
	}
	
	/**
	 * Kill your bot (useful if you think you're trapped somewhere)
	 */
	public boolean performSuicide(final String id){			
		ClientResponse response = service.path("players").path(id).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		return response.getStatus() == 204;
	}
	
	/**
	 * @param hostUrl http://host:port/
	 * @param logging
	 */
	public ClientApi(final String hostUrl, final boolean logging) {
		super();
		this.hostUrl = hostUrl;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service = client.resource(getBaseURI());
		if (logging) {
			client.addFilter(new LoggingFilter());
		}
	}
	
	private URI getBaseURI() {
		return UriBuilder.fromUri(hostUrl).build();
	}

}
