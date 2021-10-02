package xyz.derkades.derkutils.caching;

public class CacheObject {
	
	public final Object object;
	public final long expire;
	
	protected CacheObject(final Object object, final long timeout){
		this.object = object;
		this.expire = System.currentTimeMillis() + timeout * 1000;
	}

}
