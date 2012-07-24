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
package game;

import game.visual.types.Bullet;
import game.visual.types.Level;
import game.visual.types.Player;
import game.visual.types.Tank;
import game.visual.types.Wall;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import rest.service.types.Action;


public class GameEngine extends Application implements FireCallback, TankOperatorCallback{
	
	public static GameEngine instance;
	
	private final boolean lastManStanding = false;
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock(); 
	private final Group root = new Group();
	private final Map<String, Player> id2Player = new HashMap<String,Player>();
	private final Map<Player, Tank> players = new HashMap<Player,Tank>();
	private final Set<Tank> tanks = new HashSet<Tank>();
	private final Set<Bullet> bullets = new HashSet<Bullet>();
	private final Random randomGenerator = new Random();
	private final AudioClip explosionSound = null;// new AudioClip("http://soundbible.com/grab.php?id=1919&type=wav"); //URL string from which to load the audio clip. This can be an HTTP, file or jar source.
	
	private Level lvl;
	private int preferedStepSize;
	
	public GameEngine(){
		instance = this; // ugly hack to javafx 2.0 launcher
	}
	
	public static void main(final String args[]) {
		launch(null);
	}

	public void register(final Player player){
		lock.writeLock().lock();
		try{
			if(player.getId()==null){
				throw new IllegalArgumentException("ongeldig id");
			}
			Player oldPlayer = id2Player.get( player.getId() );
			if(oldPlayer == null){
				id2Player.put(player.getId(), player);
				players.put(player,createTank(player));	
			}else{
				throw new IllegalArgumentException("ongeldig id");			
			}
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	public Player findPlayer(final String id){
		lock.readLock().lock();
		try{
			return id2Player.get( id );			
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public void perform(final Player player, final Action action){
		lock.writeLock().lock();
		try{
			players.get(player).addAction(action);
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public void fire(final Player player){
		lock.writeLock().lock();
		try{
			createBullet(players.get(player));
		}finally{
			lock.writeLock().unlock();
		}
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setResizable(false);
		int w = 1024;
		int h = 768; 
		primaryStage.setScene(new Scene(root, w, h-10));
		primaryStage.show();

		lvl = new Level(w, h, 1);
		
		preferedStepSize = lvl.getCharHeight();
		
		root.getChildren().add(lvl.node);

		uglyGameLoop2();

	}

	// TODO should be a timeline (executed on jfx, at least the collision detection
	private void uglyGameLoop() {
//		new Thread() {
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						Thread.sleep(50);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					lock.writeLock().lock();
//					try{	
//						for (Tank t : tanks) {
//							t.tick();
//						}
//						for (Bullet b : bullets) {
//							b.tick();
//						}
//						uglyCollisionDetection();
//					}finally{
//						lock.writeLock().unlock();
//					}
//				}
//			}
//		}.start();
	}
	
	private void uglyGameLoop2() {
		final Duration oneFrameAmt = Duration.millis(1000/60);
		final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
				new EventHandler() {
		
		
		@Override
		public void handle(final Event arg0) {
			lock.writeLock().lock();
			try{	
				for (Tank t : tanks) {
					t.tick();
				}
				for (Bullet b : bullets) {
					b.tick();
				}
				uglyCollisionDetection();
			}finally{
				lock.writeLock().unlock();
			}
			
		}
		}); // oneFrame

		TimelineBuilder.create()
		   .cycleCount(Animation.INDEFINITE)
		   .keyFrames(oneFrame)
		   .build()
		   .play();
	}

	

	private void uglyCollisionDetection() {
		for (Bullet bullet : new LinkedList<Bullet>(bullets)) {
			for (Tank tank : new LinkedList<Tank>(tanks)) {
				if (bullet.collidesWith(tank)) {
					if (bullet.getOwner() != tank) {
						System.out.println(tank + " got hit by " + bullet.getOwner());
						removeBullet(bullet);
						bullet.getOwner().getPlayer().score();
						killAndRespawn(tank);
					}
				}
			}
		}
		for (Tank tank1 : new LinkedList<Tank>(tanks)) {
			for (Tank tank2 : new LinkedList<Tank>(tanks)) {
				if (tank1.collidesWith(tank2)) {
					if (tank1.isMoving()) {
						tank1.stop();
					}
					if (tank2.isMoving()) {
						tank2.stop();
					}
				}
			}
		}
		
		for (Tank tank1 : new LinkedList<Tank>(tanks)) {
			if (tank1.isMoving()) {
				for (Wall wall : lvl.walls) {
					if(tank1.collidesWith(wall)){
						System.out.println("chrash");
					}
					if (tank1.collidesWith(wall) && tank1.shouldStopMovingOnCollisionWith(wall)) {
						tank1.stop();
						System.out.println(tank1.getPlayer()+ " drove against the wall");
					}
				}

			}
		}
		for (Bullet bullet : new LinkedList<Bullet>(bullets)) {
			for (Wall wall : lvl.walls) {
				if (bullet.collidesWith(wall)) {
					removeBullet(bullet);
				}
			}
		}
	}
	
	@Override
	public void interupt(final Player p) {
		killAndRespawn(p);	
	}

	public void killAndRespawn(final Player p) {
		Tank t = players.get(p);
		killAndRespawn(t);
	}


	
	private void killAndRespawn(final Tank t) {
		if(explosionSound!=null){
			explosionSound.play();
		}
		removeTank(t);
		t.getPlayer().die();
		if(!lastManStanding){
			createTank(t.getPlayer());
		}
	}

	// TODO should be on fx thread
	private Tank createTank(final Player player) {
		Tank newTank = null;
		// find free spot
		while( newTank==null){
			boolean ok = true;
			int x2 = 30 + randomGenerator.nextInt(870);
			int y2 = 50 + randomGenerator.nextInt(600);
			int x = x2 - (x2 % preferedStepSize);
			int y = y2 - (y2 % preferedStepSize);
			if(x%preferedStepSize!=0 || y%preferedStepSize!=0){
				throw new RuntimeException("HHHHHHHHUHUHUH");
			}
			newTank = createTankAt(player, x, y);
			for (Tank other : new LinkedList<Tank>(tanks)) {
				if (other.collidesWith(newTank)) {
					ok = false;
					break;
				}
			}
			for (Wall wall : lvl.walls) {
				if (newTank.collidesWith(wall)) {
					ok = false;
					break;
				}
			}
			if(!ok){			
				removeTank(newTank);
				newTank = null;
			}
			
		}
		players.put(player, newTank);
		return newTank;
	}

	private void removeBullet(final Bullet b) {
		bullets.remove(b);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				b.stop();
				root.getChildren().remove(b);
			}
		});
	}

	private void removeTank(final Tank t) {
		tanks.remove(t);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				t.stop();
				root.getChildren().remove(t);
			}
		});
	}

	private Tank createTankAt(final Player p, final int x, final int y) {
		final Tank t = new Tank(p, x, y, 0, preferedStepSize, this, this);
		tanks.add(t);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				root.getChildren().add(t);
			}
		});
		return t;
	}

	private Bullet createBullet(final Tank t) {
		final Bullet b = new Bullet(t, (int) t.getMiddleX(), (int) t.getMiddleY(), (int) t.getRadius());
		bullets.add(b);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				root.getChildren().add(b);
			}
		});

		b.addAction(Action.FORWARD);
		return b;
	}

	
	public Set<Tank> getTankNodes(){
		lock.readLock().lock();
		try{
			return new HashSet<Tank>(tanks);
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public Set<Bullet> getBulletNodes(){
		lock.readLock().lock();
		try{
			return new HashSet<Bullet>(bullets);
		}finally{
			lock.readLock().unlock();
		}
	}
	public Set<Node> getWallNodes(){
		lock.readLock().lock();
		try{
			return new HashSet<Node>(lvl.node.getChildren());
		}finally{
			lock.readLock().unlock();
		}
	}
	public Set<Player> getPlayers(){
		lock.readLock().lock();
		try{
			return new HashSet<Player>(players.keySet());
		}finally{
			lock.readLock().unlock();
		}
	}


}
