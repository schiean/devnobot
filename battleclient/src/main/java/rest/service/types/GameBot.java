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
public class GameBot extends GameObject {
	
	private int distancePerStep;
	private double speed; // pixel per msec
	private String player;
	private Orientation lastKnownOrientation;
	
	
	public Orientation getLastKnownOrientation() {
		return lastKnownOrientation;
	}

	public void setLastKnownOrientation(final Orientation lastKnownOrientation) {
		this.lastKnownOrientation = lastKnownOrientation;
	}

	public int getDistancePerStep() {
		return distancePerStep;
	}
	
	public void setDistancePerStep(final int distancePerStep) {
		this.distancePerStep = distancePerStep;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(final double speed) {
		this.speed = speed;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(final String player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "GameBot [getDistancePerStep()=" + getDistancePerStep()
				+ ", getSpeed()=" + getSpeed() + ", getPlayer()=" + getPlayer()
				+ ", getX()=" + getX() + ", getY()=" + getY() + ", getWidth()="
				+ getWidth() + ", getHeight()=" + getHeight() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	
	
	
}
