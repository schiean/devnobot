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

import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * We use ASCII art for level design, use 64 * 48 to create 16*16 squares for 1024x768
 *  
 * @author arjen
 *
 */
public class Level {
	
	String[] level0 = new String[]{ 
		       "################################################################", // 64 * 48 
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
			   "################################################################"
		       };
	String[] level1 = new String[]{ 
		       "################################################################", // 64 * 48 
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#       ####   #### #   # #   #  ##  #    ##   ##  #   #       #",
		       "#       #   #  #    #   # ##  # #  # #   #  # #     # #        #",
		       "#       #   #  #### #   # # # # #  # #   #  # # ##   #         #",
		       "#       #   #  #     # #  #  ## #  # #   #  # # ##  #          #",
		       "#       ####   ####   #   #   #  ##  ###  ##   ##  #           #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#        #############################################         #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#        #############################################         #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#        #############################################         #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#        #############################################         #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                                                              #",
			   "################################################################"
		       };

	String[] level2 = new String[]{ 
		       "################################################################", // 64 * 48 
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#      ##############                      ##############      #",
		       "#      ##############                      ##############      #",
		       "#                                                              #",
		       "#                                                              #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#        ##############################################        #",
		       "#        ##############################################        #",
		       "#        ##                                          ##        #",
		       "#        ##                                          ##        #",
		       "#        ##                                          ##        #",
		       "#        ##                                          ##        #",		       
		       "#                                                              #",
		       "#                                                              #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#        ##                                          ##        #",
		       "#        ##                                          ##        #",
		       "#        ##                                          ##        #",
		       "#        ##############################################        #",
		       "#        ##############################################        #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#                              ##                              #",
		       "#                                                              #",
		       "#                                                              #",
		       "#      ###############                    ###############      #",
		       "#      ###############                    ###############      #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
		       "#             ##                                ##             #",
			   "################################################################"
};

	
	public Group node = new Group();
	public List<Wall> walls = new LinkedList<Wall>();
	
	private final int wl;
	private final int wh;	
	private final double charWidth;
	private final double charHeight;
	
	private final String[][] levels = {level0,level1, level2};
	private final Color[] levelColors = {Color.SANDYBROWN,Color.ALICEBLUE, Color.DARKGREEN};
	private final Color[] levelColors2 = {Color.BEIGE,Color.BLUEVIOLET, Color.GOLD};
	
	public Level( final int w,final int h, final int levelNr){
		String[] level = levels[levelNr];
		wl = level[0].length();
		wh = level.length;
		System.out.println(wl+" "+wh);
		charWidth = w/wl;
		charHeight = h/wh;
		
		Stop[] stops = new Stop[] { new Stop(0, levelColors[levelNr]), new Stop(1, levelColors2[levelNr])};
	    LinearGradient lg = new LinearGradient(0, 0, 0.5, 0, true, CycleMethod.REFLECT, stops); 
		Rectangle background = new Rectangle(0, 0, w, h);
		background.setFill(lg);
		
		node.getChildren().add(background);
		
		for(int i=0;i<wh;i++){
			for(int j=0;j<wl;j++){				
				addBlock(i,j,level);
			}
		}		
		// TODO caching en minimaliseren gaat niet samen ?
		node.setCache(true); 
		node.setCacheHint(CacheHint.SPEED);
	}

	private void addBlock(final int i, final int j, final String[] level) {
		if(level[i].charAt(j)=='#'){
			Wall w = new Wall((int)(j*charWidth), (int)(i*charHeight),(int)charWidth, (int)charHeight);
			node.getChildren().add(w);
			walls.add(w);
		}
	}

	public int getCharWidth() {
		return (int)charWidth;
	}

	public int getCharHeight() {
		return (int)charHeight;
	}
	
	
}
