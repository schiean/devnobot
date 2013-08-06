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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Wall extends CollisionParent {

	private final int x;
	private final int y;
	private final int length;
	private final int width;
	
	public Wall(final int x, final int y, final int length, final int width) {
		super();
		this.x = x;
		this.y = y;
		this.length = length;
		this.width = width;
		Rectangle r = new Rectangle(x,y,length, width);
		r.setFill(Color.BLACK);
		getChildren().add(r);
	}
	
}
