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

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import rest.service.types.Action;
import rest.service.types.Orientation;

public abstract class MovingParent extends CollisionParent {

	private final Object actionLock = new Object();
	private final List<Action> actions = new LinkedList<Action>();

	protected double angle = 90;
	
	protected Orientation orientation;
	protected Transition current = null;
	protected TranslateTransition left = new TranslateTransition();
	protected TranslateTransition right = new TranslateTransition();
	protected TranslateTransition up = new TranslateTransition();
	protected TranslateTransition down = new TranslateTransition();

	public MovingParent(){
		getForAngle(angle);// used for the side effect of setting orientation
	}

	public Orientation getLastKnownOrientation(){
		return orientation;
	}
	
	protected void start(final Action action){
		current = getForAngle(angle);
		current.play();		
	}
	

	public void stop() {
		if (current != null) {
			current.stop();
		}
	}
	
	/**
	 * 
	 * @return true if we are actually running a transformation into a direction (up,down,left,right)
	 */
	public boolean isMoving(){
		return current!=null && current.getStatus() == Animation.Status.RUNNING && (current == up || current==down || current == left || current == right );
	}
		
	public void addAction(final Action action) {
		synchronized (actionLock) {
			actions.add(action);	
		}
	}

	public void tick(){
		synchronized (actionLock) {			
			if( (current == null || current.getStatus() == Animation.Status.STOPPED)
					&& !actions.isEmpty()){
				start(actions.get(0));
				actions.remove(0);
			}
			getForAngle(angle);// used for the side effect of setting orientation
		}
	}

	public int getActionQueueSize(){
		return actions.size();
	}
	
	
	protected Transition getForAngle(double angle){
		
		while(angle<0){
			angle = 360 + angle;
		}
		angle = angle % 360;
		if(angle==0){
			orientation = Orientation.UP;
			return up;
		}
		if(angle==90){
			orientation = Orientation.RIGHT;
			return right;
		}
		if(angle==180){
			orientation = Orientation.DOWN;
			return down;
		}
		if(angle==270){			
			orientation = Orientation.LEFT;
			return left;
		}
		System.out.println("weird angle"+angle);
		return null;
	}

}
