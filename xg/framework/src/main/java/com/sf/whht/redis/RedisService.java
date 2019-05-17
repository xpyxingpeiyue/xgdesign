package com.sf.whht.redis;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.Resource;

@Component
public class RedisService {
    private static final Log log = LogFactory.get();
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存放入
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    /**
     * 根据value从一个set中查询,是否存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    public long sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }
    
    /**
	 * 向一张hash表中放入数据,如果不存在将创建 
	 * 
	 * @param key   键 
	 * @param item  项 
	 * @param value 值 
	 * @return true 成功 false失败 
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * 向一张hash表中获取数据
	 *
	 * @param key   键
	 * @param item  项
	 * @return value 值
	 */
	public Object hget(String key, String item) {
		try {
			return redisTemplate.opsForHash().get(key, item);
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	/**
	 * 判断hash表中是否有该项的值 
	 * @param key  键 不能为null 
	 * @param item 项 不能为null 
	 * @return true 存在 false不存在 
	 */
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	public boolean delete(String key) {
		return redisTemplate.delete(key);
	}
	/**
	 *  删除hash表中的值
	 *
	 * @param key  键 不能为
	 * @param item 项 可以使多个 不能为
	 */
	public Long hdel(String key, Object... item) {
		return redisTemplate.opsForHash().delete(key, item);
	}
	/**
	 *   获取hashKey对应的所有键值 
	 * 
	 * @param key 键 
	 * @return 对应的多个键值 
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}
}
