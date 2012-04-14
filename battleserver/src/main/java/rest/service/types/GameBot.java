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
	private int actionDurationInMs; 
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
	public String getPlayer() {
		return player;
	}
	public void setPlayer(final String player) {
		this.player = player;
	}
	public int getActionDurationInMs() {
		return actionDurationInMs;
	}
	public void setActionDurationInMs(final int actionDurationInMs) {
		this.actionDurationInMs = actionDurationInMs;
	}

	@Override
	public String toString() {
		return "GameBot [distancePerStep=" + distancePerStep
				+ ", actionDurationInMs=" + actionDurationInMs + ", player="
				+ player + ", lastKnownOrientation=" + lastKnownOrientation
				+ ", toString()=" + super.toString() + "]";
	}	
}
