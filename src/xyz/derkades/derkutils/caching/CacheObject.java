package xyz.derkades.derkutils.caching;

import org.jetbrains.annotations.NotNull;

public class CacheObject {
	
	public final @NotNull Object object;
	public final long expire;
	
	protected CacheObject(final @NotNull Object object, final long timeout){
		this.object = object;
		this.expire = System.currentTimeMillis() + timeout * 1000;
	}

}
