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

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class Parent {

	protected Node node;
	
	public Node getVisibleNode(){
		return node;
	}
	
	public Node getCollisionNode(){
		return node;
	}
	
	public Bounds getCollisionBounds(){
		return getCollisionNode().localToScene(getCollisionNode().getLayoutBounds());
	}
	
	
	/**
	 * @param other != null
	 * @return boundingparents.overlap
	 */
	public boolean collidesWith(final Parent other){
		Bounds myBounds = this.getCollisionBounds();
		Bounds hisBounds = other.getCollisionBounds();		
		return this!=other && myBounds.intersects(hisBounds);
	}
	
	/** 
	 * @param other
	 * @return true if the other is right of this (this is left of other)
	 */
	public boolean isLeftOf(final Parent other){
		Bounds myBounds = this.getCollisionBounds();
		Bounds hisBounds = other.getCollisionBounds();
		return myBounds.getMinX() <= hisBounds.getMinX();
	}
	
	/**
	 * @param other
	 * @return true if the other is below this (this is above other)
	 */
	public boolean isAbove(final Parent other){
		Bounds myBounds = this.getCollisionBounds();
		Bounds hisBounds = other.getCollisionBounds();
		return myBounds.getMinY() <= hisBounds.getMinY();		
	}
}
