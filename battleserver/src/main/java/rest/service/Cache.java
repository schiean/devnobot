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
package rest.service;

import java.util.Date;

/**
 * implements a simple cache
 * not-threadsafe. Users should implement threadsafety when needed.
 * 
 * @author arjen
 */
public class Cache<T>{
	
	private final int thressholdInMs;
	
	private long createDate;
	private T cachedObject;
	
	/**
	 * @param thressholdInMS
	 */
	public Cache(final int thressholdInMS){
		this.thressholdInMs = thressholdInMS;
	}
	
	/**
	 * @return true, if the object was last refreshed more then thressholdInMs ago
	 */
	public boolean needsUpdate(){
		return createDate + thressholdInMs < new Date().getTime();
	}
	
	/**
	 * replace the cached value and reset time-counter
	 * @param newCachedObject
	 */
	public void set(final T newCachedObject){
		this.cachedObject = newCachedObject;
		createDate = new Date().getTime();
	}
	
	/**
	 * @return cachedObject (does not chech needsUpdate())
	 */
	public T get(){
		return cachedObject;
	}
	
}