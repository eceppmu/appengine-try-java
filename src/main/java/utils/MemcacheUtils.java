package utils;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MemcacheUtils {
	public static String getCacheKey(String key) {
	    MemcacheService cacheService = MemcacheServiceFactory.getMemcacheService();
	    
	    return (String)cacheService.get(key);
	}
	
	public static void addCacheKey(String key, String value) {
	    MemcacheService cacheService = MemcacheServiceFactory.getMemcacheService();
	    	    
	    cacheService.put(key, value);
	    
	}
}
