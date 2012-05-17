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

import game.visual.types.Player;
import game.visual.types.Tank;
import javafx.geometry.Bounds;
import rest.service.types.GameBot;
import rest.service.types.GameBullet;
import rest.service.types.GameObject;
import rest.service.types.GameObstacle;
import rest.service.types.GamePlayer;

public class Mapper {
	
	public GameBullet createBullet(final Bounds n) {
		GameBullet bullet =new GameBullet();
		create(bullet, n);
		return bullet;
	}

	public GameBot createBot(final Tank t) {
		GameBot bot = new GameBot();
		create(bot, t.getCollisionBounds());				
		bot.setLastKnownOrientation(t.getLastKnownOrientation());
		bot.setActionDurationInMs(t.getActionduration());
		bot.setDistancePerStep(t.getStepSize());
		bot.setPlayer(t.getPlayer().getName());
		bot.setQueueLength(t.getActionQueueSize());
		return bot;
	}

	public GamePlayer createPlayer(final Player p) {
		GamePlayer player = new GamePlayer();
		// dont publish ID
		player.setName(p.getName());
		player.setKills(p.getKills());
		player.setDeads(p.getDeads());
		player.setColor(p.getColor().toString()); 
		return player;
	}
	
	public GameObstacle createObstacle(final Bounds n){
		GameObstacle obs = new GameObstacle();
		create(obs, n);
		return obs;
	}

	private GameObject create(final GameObject o, final Bounds bounds){
		o.setHeight((int)bounds.getHeight());
		o.setWidth((int)bounds.getWidth());
		o.setX((int)bounds.getMinX());
		o.setY((int)bounds.getMinY());
		return o;
	}
	
}
