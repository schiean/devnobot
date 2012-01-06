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
public class GameObject {
		
	private int x;
	private int y;
	private int width;
	private int height;
	
	public int getX() {
		return x;
	}
	public void setX(final int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(final int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(final int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(final int height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "GameObject [ x=" + x + ", y=" + y
				+ ", width=" + width + ", height=" + height + "]";
	}
	
	
}
