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

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransitionBuilder;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class Bullet extends MovingParent {
	
	private final Tank owner;
    
	public Bullet(final Tank owner, final int x, final int y, final int r){
		super();
		this.owner = owner;
		this.angle = r;
		this.node =  bullet(x,y);
		
		double actionTime = 5000.0;
		right = TranslateTransitionBuilder.create()
          .duration(new Duration(actionTime))
          .node(node)
          .byX(1500)
          .interpolator(Interpolator.LINEAR)
          .cycleCount(1)
        .build();
        
		left = TranslateTransitionBuilder.create()
        .duration(new Duration(actionTime))
        .node(node)
        .byX(-1500)
        .interpolator(Interpolator.LINEAR)
        .cycleCount(1)
      .build();
		
		up = TranslateTransitionBuilder.create()
        .duration(new Duration(actionTime))
        .node(node)
        .byY(-1500)
        .interpolator(Interpolator.LINEAR)
        .cycleCount(1)
      .build();
		
		down = TranslateTransitionBuilder.create()
        .duration(new Duration(actionTime))
        .node(node)
        .byY(1500)
        .interpolator(Interpolator.LINEAR)
        .cycleCount(1)
      .build();      
     }
	
	public double getX() {
		return node.getBoundsInParent().getMinY();
	}

	public double getY() {
		return node.getBoundsInParent().getMinY();
	}
	
	private Node bullet(final int x, final int y) {
		Circle c = new Circle(x, y, 2);
		c.setFill(Color.BLACK);
		return c;
	}

	public Tank getOwner(){
		return owner;
	}
}
