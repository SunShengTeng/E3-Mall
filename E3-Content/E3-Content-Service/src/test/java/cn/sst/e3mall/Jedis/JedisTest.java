package cn.sst.e3mall.Jedis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sst.e3mall.common.Jedis.JedisClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 测试Jedis链接单机版、集群版
 * 
 * @author sunshengteng
 *
 */
public class JedisTest {

	/**
	 * 测试单机版（使用链接池）
	 */
	@Test
	public void test01() {
		// 1、初始化池子
		JedisPool jedisPool = new JedisPool("192.168.169.159", 6379);
		// 2、从池子中获取jedis对象
		Jedis jedis = jedisPool.getResource();
		// 3、操作数据库
		String string = jedis.get("num");
		System.out.println(string);
		// 4、归还资源
		jedis.close();
		jedisPool.close();

	}

	/**
	 * 测试集群版
	 * @throws IOException 
	 */
	@Test
	public void test02() throws IOException {

		//1、创建集群链接的配置信息
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.169.159", 7001));
		nodes.add(new HostAndPort("192.168.169.159", 7002));
		nodes.add(new HostAndPort("192.168.169.159", 7003));
		nodes.add(new HostAndPort("192.168.169.159", 7004));
		nodes.add(new HostAndPort("192.168.169.159", 7005));
		nodes.add(new HostAndPort("192.168.169.159", 7006));
		//2、创建集群
		JedisCluster cluster = new JedisCluster(nodes);
		System.out.println(cluster.get("ids3"));
		//3、归还资源
		cluster.close();
	}
	
	@Test
	public void testJedisClient() throws Exception {
		//初始化Spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:Spring/ApplicationContext-Redis.xml");
		//从容器中获得JedisClient对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("first", "100");
		String result = jedisClient.get("first");
		System.out.println(result);
		
	}

}
