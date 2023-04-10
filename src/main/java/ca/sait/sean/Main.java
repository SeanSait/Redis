package ca.sait.sean;

import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {

	public static void main(String[] args) {
		// Init with multi-threads using the provided pool.
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

		// We place the Jedis instance in a try and catch as it'll auto close due to Closable.
		try (Jedis jedis = pool.getResource()) {
			// Set single key and value.
			jedis.set("key", "value");
			String value = jedis.get("key");
			assert value.equals("value");

			// Set a list of elements.
			jedis.zadd("list", 0, "dog");
			jedis.zadd("list", 0, "cat");
			// Collect all the elements in the list starting at 0, and we use -1 to indicate that there is no limit.
			// Grab all the values. The end number parameter can be set to 5, and the list will then contain 5 elements.
			List<String> list = jedis.zrange("list", 0, -1);
			System.out.println(Arrays.toString(list.toArray(String[]::new)));
		}
	}

}
