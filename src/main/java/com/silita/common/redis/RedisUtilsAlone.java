package com.silita.common.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * redis连接工具，供本次测试非集群使用
 */
public class RedisUtilsAlone {
    private Logger logger = Logger.getLogger(RedisUtilsAlone.class);

    private JedisPool jedisPool;

    public RedisUtilsAlone() {
        if (null == jedisPool) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(50);
            poolConfig.setMaxWaitMillis(180000);
            poolConfig.setMaxIdle(50);
            jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        }
    }

    public synchronized Jedis getJedis() {
        return jedisPool.getResource();
    }

    public boolean hexists(String key, String field) {
        boolean exists = false;
        Jedis jedis = null;
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

    public void hset(String key, String field, String value) {
        Jedis jedis = null;
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
