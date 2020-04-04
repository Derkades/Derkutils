package xyz.derkades.derkutils.caching;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.Validate;

public class Cache {
	
	private static final int DEFAULT_CACHE_DURATION = 60;
	
	private static final Map<String, CacheObject> CACHE_OBJECT_MAP = new ConcurrentHashMap<>();
	
	/**
	 * Caches object for the specified amount of time.
	 * @param identifier
	 * @param object
	 * @param timeout In seconds. If set to <= 0, cache indefinitely
	 */
	public static void set(final String identifier, final Object object, long timeout) {
		Validate.notNull(identifier);
		Validate.notNull(object);
		
		if (timeout <= 0){
			timeout = Long.MAX_VALUE;
		}
		
		final CacheObject cachedObject = new CacheObject(object, timeout);
		CACHE_OBJECT_MAP.put(identifier, cachedObject);
	}
	
	/**
	 * Caches object for an hour. <br>
	 * See also: {@link #addCachedObject(String, Object, long)}
	 */
	public static void set(final String identifier, final Object object) {
		set(identifier, object, DEFAULT_CACHE_DURATION);
	}
	
	public static <T> Optional<T> get(final String identifier) {
		Validate.notNull(identifier);
		
		final CacheObject cache = CACHE_OBJECT_MAP.get(identifier);
		
		if (cache == null) {
			return Optional.empty();
		}
		
		if (cache.expire < System.currentTimeMillis()) {
			CACHE_OBJECT_MAP.remove(identifier);
			return Optional.empty();
		} else {
			@SuppressWarnings("unchecked")
			final T t = (T) cache.object;
			return Optional.of(t);
		}
	}
	
	public static void remove(final String identifier) {
		Validate.notNull(identifier);
		CACHE_OBJECT_MAP.remove(identifier);
	}
	
	/**
	 * Clean cache, removing any expired objects. This can be an expensive
	 * operation, it should be run asynchronously.
	 * @return Number of objects removed from cache
	 */
	public static int cleanCache(){
		final Stack<String> toRemove = new Stack<>();
		
		CACHE_OBJECT_MAP.forEach((k, v) -> {
			if (v.expire < System.currentTimeMillis()) {
				toRemove.push(k);
			}
		});
		
		final int i = toRemove.size();
		
		while (!toRemove.isEmpty()) {
			removeCachedObject(toRemove.pop());
		}
		
		return i;
	}
	
	public static int size(){
		return CACHE_OBJECT_MAP.size();
	}
	
	@Deprecated
	public static void addCachedObject(final String identifier, final Object object, final long timeout){
		set(identifier, object, timeout);
	}
	
	@Deprecated
	public static void addCachedObject(final String identifier, final Object object){
		set(identifier, object);
	}
	
	@Deprecated
	public static Object getCachedObject(final String identifier){
		final CacheObject cache = CACHE_OBJECT_MAP.get(identifier);
		
		if (cache == null) {
			return null;
		}
		
		if (cache.expire < System.currentTimeMillis()) {
			CACHE_OBJECT_MAP.remove(identifier);
			return null;
		} else {
			return cache.object;
		}
	}
	
	@Deprecated
	public static void removeCachedObject(final String identifier){
		CACHE_OBJECT_MAP.remove(identifier);
	}

}
