package com.silita.consumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * Created by dh on 2017/7/24.
 */
@Component
public class RedisUtils {
    private Logger logger = Logger.getLogger(RedisUtils.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;//注入ShardedJedisPool

    public synchronized ShardedJedis getJedis() {
        return shardedJedisPool.getResource();
    }

    /**
     * 删除元素
     *
     * @param key
     */
    public void del(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (RuntimeException e) {
            logger.error("del key RuntimeException error : " + e, e);
        } catch (Exception e) {
            logger.error("del key error : " + e, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean hexists(String key, String field) {
        boolean exists = false;
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();
            exists = jedis.hexists(key, field);
        } catch (Exception e) {
            logger.error(e, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return exists;
    }

    public Long hdel(String key, String field) {
        ShardedJedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            result = jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error(e, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public void hset(String key, String field, String value) {
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
