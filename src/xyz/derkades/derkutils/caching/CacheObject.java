package xyz.derkades.derkutils.caching;

import org.checkerframework.checker.nullness.qual.NonNull;

public class CacheObject {
	
	public final @NonNull Object object;
	public final long expire;
	
	protected CacheObject(final @NonNull Object object, final long timeout){
		this.object = object;
		this.expire = System.currentTimeMillis() + timeout * 1000;
	}

}
