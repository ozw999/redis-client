package com.obbo.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ou
 * @date 2022/1/17
 */
public class JedisDemo {

    public String clientGet(String key) {
        Jedis client = new Jedis("10.200.112.133", 9496);
        String value = client.get(key);
        client.close();
        return value;
    }

    public String poolGet(String key) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        JedisPool pool = new JedisPool(config, "10.200.112.133", 9496, 2000, null);
        Jedis jedis = pool.getResource();
        String value = jedis.get(key);
        jedis.close();
        pool.close();
        return value;
    }

    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.70.3.127:9496");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxTotal(20);
        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, config);
        Jedis jedis = pool.getResource();
        jedis.get("test");
        jedis.close();
        pool.close();
    }

}
