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
package game.visual.types;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class Player {
	private final String id;
	private final String name;
	private int kills = 0;
	private int deads = 0;
	private final Color color;
	private final TextField textField = new TextField();
	
	public Player(final String name, final Color color, final String id) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;

		invalidate();
	}

	public String getId() {
		return id;
	}

	public int getKills(){
		return kills;
	}
	
	public int getDeads(){
		return deads;
	}
	
	public void score(){
		kills++;
		invalidate();
	}
	
	public void die(){
		deads++;
		invalidate();
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}
	
	@Override
	public String toString() {
		return name + " " + kills + "/" + deads;
	}

	public void invalidate(){
		textField.setText(toString());
	}

	public ObservableValue<? extends String> getPlayerProperty() {
		return textField.textProperty();
	}	
	
}
