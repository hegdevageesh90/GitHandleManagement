package org.vaadin.samples.githandlemanagement;

import redis.clients.jedis.Jedis;

/**
 * @author vageeshhegde
 */
public class GithandlesCache {
    private Jedis jedis;

    public GithandlesCache(){
        jedis = new Jedis("localhost");
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
