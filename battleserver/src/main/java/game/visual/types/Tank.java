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

import game.FireCallback;
import game.TankOperatorCallback;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import rest.service.types.Action;

public class Tank extends MovingParent {

	private final static double actionDuration = 600;//1500;
	private final static double fireDuration =  actionDuration * 2 -500;

	private final TankOperatorCallback operator; 
	private final Player player;
	private final FireCallback callback;
	private final int stepSize;
	private final Node tank;
	
	protected TranslateTransition fire = new TranslateTransition();
	protected RotateTransition turn_left = new RotateTransition();
	protected RotateTransition turn_right = new RotateTransition();
	
	public Tank(final Player player, final int x, final int y, final int r,final int stepSize, final FireCallback callback, final TankOperatorCallback operator) {
		this.player = player;
		this.operator = operator;
		Text t = new Text(x, y+50, player.toString());
		t.textProperty().bind(player.getPlayerProperty());
		this.tank = tank(x, y, r, player.getColor());
		this.getChildren().add(t);
		this.getChildren().add(tank);
			
		this.callback = callback;
		this.stepSize = stepSize;
		
		right = TranslateTransitionBuilder.create()
				.duration(new Duration(actionDuration))
				.node(this)
				.byX(stepSize)
				.interpolator(Interpolator.LINEAR)
				.cycleCount(1)
				.build();

		left = TranslateTransitionBuilder.create()
				.duration(new Duration(actionDuration))
				.node(this)
				.byX(-stepSize)
				.interpolator(Interpolator.LINEAR)
				.cycleCount(1)
				.build();
		up = TranslateTransitionBuilder.create()
				.duration(new Duration(actionDuration))
				.node(this)
				.byY(-stepSize)
				.interpolator(Interpolator.LINEAR)
				.cycleCount(1)
				.build();
		down = TranslateTransitionBuilder.create()
				.duration(new Duration(actionDuration))
				.node(this)
				.byY(stepSize)
				.interpolator(Interpolator.LINEAR)
				.cycleCount(1)
				.build();

		// fire is not really a action on tank but a new object(bullet) so we use a standing still of 1.5s
		fire = TranslateTransitionBuilder.create()
				.duration(new Duration(fireDuration))
				.node(this)
				.byX(0)
				.interpolator(Interpolator.LINEAR)
				.cycleCount(1)
				.build();

		
		turn_right = RotateTransitionBuilder.create()
				.duration(new Duration(actionDuration))
				.node(this)
				.byAngle(90)
				
				.build();

		turn_left = RotateTransitionBuilder.create()
				.duration(new Duration(actionDuration))
				.node(this)
				.byAngle(-90)
				.build();		
		
	}
	
	
	/**
	 * @param other (or null)
	 * @return
	 */
	public boolean shouldStopMovingOnCollisionWith(final CollisionParent other) {
		return !movingAwayFromCollision(other);		       
	}
	
	private boolean movingAwayFromCollision(final CollisionParent other) {
		if(other == null){
			return true; // null means its ok
		}
		// if our current movement is in the direction of our relative location from other we are driving away which is fine.
		if(current == up){ 
			return this.isAbove(other);
		}else if(current == down){
			return !this.isAbove(other);
		}else if(current == left){
			return this.isLeftOf(other);
		}else if(current == right){
			return !this.isLeftOf(other);
		}		
		return true; // if we got here we are not moving at all, which means we are not (not moving away)
	}



	@Override
	protected void start(final Action action) {
		switch (action) {
			case FORWARD:
				current = getForAngle(angle);
				break;
			case BACKWARD:
				current = getForAngle(angle + 180);
				break;
			case TURN_LEFT:
				current = turn_left;
				angle -= 90;
				break;
			case TURN_RIGHT:
				current = turn_right;
				angle += 90;
				break;
			case FIRE:
				current = fire;
				callback.fire(player);
				break;
		}
		current.play();
	}


	public double getMiddleX() {
		return (getCollisionBounds().getMinX() + getCollisionBounds().getMaxX()) / 2;
	}

	public double getMiddleY() {
		return (getCollisionBounds().getMinY() + getCollisionBounds().getMaxY()) / 2;
	}

	public Player getPlayer() {
		return player;
	}

	public double getRadius() {
		return angle;
	}

	public double getX() {
		return getCollisionBounds().getMinX();
	}

	public double getY() {
		return getCollisionBounds().getMinY();
	}

	public int getStepSize() {
		return stepSize;
	}

	private Group tank(final int x, final int y, final int r, final Color color) {
		final Group tank = new Group();

		Line l1 = new Line(10, 0, 10, 10);
		Line l2 = new Line(20, 0, 20, 10);
		Line l3 = new Line(30, 0, 30, 10);
		Line l4 = new Line(40, 0, 40, 10);
		Line l5 = new Line(50, 0, 50, 10);

		Group wtop = new Group();
		Rectangle top = new Rectangle(0, 0, 60, 10);
		top.setFill(Color.GRAY);
		top.setStrokeWidth(1);
		top.setStroke(Color.BLACK);
		wtop.setEffect(new InnerShadow());

		wtop.getChildren().add(top);
		wtop.getChildren().add(new Line(10, 0, 10, 10));
		wtop.getChildren().add(new Line(20, 0, 20, 10));
		wtop.getChildren().add(new Line(30, 0, 30, 10));
		wtop.getChildren().add(new Line(40, 0, 40, 10));
		wtop.getChildren().add(new Line(50, 0, 50, 10));

		Rectangle body = new Rectangle(5, 10, 40, 18);

		Stop[] stops = new Stop[] { new Stop(0, Color.BLACK),new Stop(1, color) };
		LinearGradient lg = new LinearGradient(0, 0, 0.5, 0, true, CycleMethod.REFLECT, stops);
		body.setFill(lg);

		Group wcannon = new Group();
		Rectangle cannon = new Rectangle(35, 16, 25, 6);
		cannon.setFill(Color.BLACK);
		Rectangle cannonHead = new Rectangle(55, 14, 5, 10);
		cannonHead.setFill(Color.BLACK);
		wcannon.getChildren().add(cannon);
		wcannon.getChildren().add(cannonHead);

		Rectangle bottom = new Rectangle(0, 28, 60, 10);
		bottom.setFill(Color.GRAY);
		bottom.setStrokeWidth(1);
		bottom.setStroke(Color.BLACK);

		Group wbottom = new Group();
		wbottom.getChildren().add(bottom);
		wbottom.getChildren().add(new Line(10, 28, 10, 38));
		wbottom.getChildren().add(new Line(20, 28, 20, 38));
		wbottom.getChildren().add(new Line(30, 28, 30, 38));
		wbottom.getChildren().add(new Line(40, 28, 40, 38));
		wbottom.getChildren().add(new Line(50, 28, 50, 38));
		wbottom.setEffect(new InnerShadow());

		tank.getChildren().add(wcannon);
		tank.getChildren().add(wtop);
		tank.getChildren().add(wbottom);
		tank.getChildren().add(body);

		DropShadow shadow = new DropShadow();
		tank.setEffect(shadow);

		tank.setLayoutX(x);
		tank.setLayoutY(y);
		tank.setRotate(r);

		tank.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {
				Bounds bounds = tank.getBoundsInParent();				
				System.out.println(event.getSceneX() + " " + event.getSceneY());
				System.out.println(bounds.getHeight() + " "+ bounds.getWidth() + " "+ bounds.getMinX() + " "+bounds.getMinY());
				System.out.println(tank.getLayoutX() +" "+tank.getTranslateX() +" "+tank.localToScene(getBoundsInLocal()) );
				if(event.isControlDown()){
					operator.interupt(player);
				}
			}
		});
		
		tank.setCache(true);
		tank.setCacheHint(CacheHint.SPEED);
		return tank;
	}
	
	@Override
	public Node getCollisionNode(){
		return tank;
	}

	public static int getActionduration() {
		return (int)actionDuration;
	}

	@Override
	public String toString() {
		return player.toString();
	}
}
