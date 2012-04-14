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
package rest.service.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class World {
	
	private List<GameBot> bots = new ArrayList<GameBot>();
	private List<GameBullet> bullets= new ArrayList<GameBullet>();
	
	public List<GameBot> getBots() {
		return bots;
	}
	public void setBots(final List<GameBot> bots) {
		this.bots = bots;
	}
	public List<GameBullet> getBullets() {
		return bullets;
	}
	public void setBullets(final List<GameBullet> bullets) {
		this.bullets = bullets;
	}
	
	
}
