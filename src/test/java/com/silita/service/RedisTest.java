package com.silita.service;

import com.silita.common.Constant;
import com.silita.common.ObjectTranscoder;
import com.silita.consumer.RedisUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

/**
 * <p>Created by mayongbin01 on 2017/3/9.</p>
 */
public class RedisTest extends ConfigTest {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Test
    public void getAndPutTest() {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.lpush("ceshi".getBytes(), ObjectTranscoder.serialize("湖南耀邦"));
            //redisUtils.hset(Constant.Cache_Company, "1123", "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    @Test
    public void getList(){
        ShardedJedis jedis = null;
        List<byte[]> list = null;
        try {
            jedis = shardedJedisPool.getResource();
            list = jedis.lrange("ceshi".getBytes(),0,0);
            if(null!= list && list.size() > 0){
                for (byte[] str : list){
                    System.out.println(ObjectTranscoder.deserialize(str));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    @Test
    public void deleteEntityMD5One() {
        ShardedJedis jedis = null;
        try {
            String md5 = "04e72b4554f516ffeb4900ad56778073";
            jedis = shardedJedisPool.getResource();
            boolean exists = jedis.hexists(Constant.Cache_Company, md5);
            if(exists){
                long result = jedis.hdel("Cache_Company", md5);
                if (result > 0) {
                    System.out.println(String.format("%s 删除成功", md5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

}