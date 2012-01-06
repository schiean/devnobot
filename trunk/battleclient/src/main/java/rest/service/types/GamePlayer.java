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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GamePlayer {

	private String name;
	private Integer code;
	private int kills;
	private int deads;
	private String color;
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Integer getCode() {
		return code;
	}
	
	public void setCode(final Integer code) {
		this.code = code;
	}
	
	public void setKills(final int kills) {
		this.kills = kills;
	}
	public int getKills() {
		return kills;
	}
	public int getDeads() {
		return deads;
	}
	public void setDeads(final int deads) {
		this.deads = deads;
	}
	public String getColor() {
		return color;
	}
	public void setColor(final String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "GamePlayer [name=" + name + ", code=" + code + ", kills=" + kills
				+ ", deads=" + deads + "]";
	}
	
	
}
