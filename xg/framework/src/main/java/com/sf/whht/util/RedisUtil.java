package com.sf.whht.util;

import com.sf.whht.redis.RedisService;

import java.util.Map;

/**
 * @author 80003301
 */
public class RedisUtil {
    private static final RedisService redisService = SpringContextUtil.getBean(RedisService.class);

    private RedisUtil() {}

    /**
     * 缓存放入
     */
    public static boolean set(String key, Object value) {
        return redisService.set(key, value);
    }

    /**
     * 根据value从一个set中查询,是否存在
     */
    public static boolean sHasKey(String key, Object value) {
        return redisService.sHasKey(key, value);
    }

    /**
     * set  将数据放入set缓存
     */
    public static long sSet(String key, Object... values) {
        return redisService.sSet(key, values);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     */
    public static boolean hset(String key, String item, Object value) {
        return redisService.hset(key, item, value);
    }

    /**
     * 向一张hash表中获取数据
     */
    public static Object hget(String key, String item) {
        return redisService.hget(key, item);
    }

    /**
     * 判断hash表中是否有该项的项
     */
    public static boolean hHasKey(String key, String item) {
        return redisService.hHasKey(key, item);
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public static boolean delete(String key) {
        return redisService.delete(key);
    }

    /**
     * 批量删除缓存
     *
     * @param key
     * @param item
     * @return
     */
    public static Long hdel(String key, Object... item) {
        return redisService.hdel(key, item);

    }

    public static Map<Object, Object> hmget(String key) {
        return redisService.hmget(key);

    }
}
