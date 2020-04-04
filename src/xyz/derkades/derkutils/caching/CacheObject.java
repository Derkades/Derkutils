package xyz.derkades.derkutils.caching;

public class CacheObject {
	
	public Object object;
	public long expire;
	
	protected CacheObject(final Object object, final long timeout){
		this.object = object;
		this.expire = System.currentTimeMillis() + timeout * 1000;
	}

}
