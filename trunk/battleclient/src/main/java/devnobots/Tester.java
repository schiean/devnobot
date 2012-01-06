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
package devnobots;

import java.awt.Color;


public class Tester {
	
	private static final String[] names = {"robocop","autobot","C-3PO","R2-D2","FemBot","T-800","T-1000"};
	private static final Color[] colors = new Color[]{Color.MAGENTA, Color.RED, Color.BLACK, Color.ORANGE, Color.DARK_GRAY, Color.CYAN, Color.ORANGE};
	
	public static void main(final String[] args) {
		String host = "http://localhost:7080/"; 
		for(int i=0; i<names.length;i++){
			new Thread( new DummyExampleBot(host, names[i], color(i) )).start();
		}
	}
	
	public static String color(final int i){
		return "#"+Integer.toHexString((colors[i].getRGB() & 0xffffff) | 0x1000000).substring(1);
	}
}
