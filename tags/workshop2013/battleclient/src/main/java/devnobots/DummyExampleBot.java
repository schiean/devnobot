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
import java.awt.Color;
import java.util.Random;

import rest.service.types.Action;
import rest.service.types.GameBot;
import rest.service.types.World;


public class DummyExampleBot implements Runnable{
	
	private final ClientApi api;
	private final Random randomGenerator = new Random();
	private final String name;
	private final String color;

	public DummyExampleBot(final String host,final String name, final String color){
		this.api = new ClientApi(host, true);
		this.name = name;
		this.color = color;
		System.out.println("name:"+name+" color:"+color+" ("+Color.decode(color).toString()+")");
	}	
	
	@Override
	public void run(){
		api.readLevel();
		
		String id = name+" our really secret id";
		
		api.createPlayer(name, color, id);
		api.readPlayers();
		
		while(true){
			Action action = Action.values()[randomGenerator.nextInt(Action.values().length)];
			api.addAction(action, id);
			World w = api.readWorldStatus();
			for(GameBot bot: w.getBots()){
				System.out.println(bot.getPlayer()+" "+bot.getLastKnownOrientation());
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
}
