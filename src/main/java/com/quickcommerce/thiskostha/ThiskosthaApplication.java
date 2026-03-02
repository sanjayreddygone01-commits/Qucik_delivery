package com.quickcommerce.thiskostha;

import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ThiskosthaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThiskosthaApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplste() {
		return new RestTemplate();
	}
	@Bean
	  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {

	    RedisTemplate<String, String> template = new RedisTemplate<>();
	    template.setConnectionFactory(connectionFactory);

	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(new StringRedisSerializer());

	    template.afterPropertiesSet();
	    return template;
	  }
}
