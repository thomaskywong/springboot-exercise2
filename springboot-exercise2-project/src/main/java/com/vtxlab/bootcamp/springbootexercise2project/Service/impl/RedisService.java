package com.vtxlab.bootcamp.springbootexercise2project.Service.impl;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.vtxlab.bootcamp.springbootexercise2project.exception.CoingeckoNotAvailableException;
import com.vtxlab.bootcamp.springbootexercise2project.infra.Syscode;


@Service
public class RedisService {
  
  private final RedisTemplate<String, String> redisTemplate;
  
  // @Autowired
  public RedisService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public String setValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    // redisTemplate.expire(key, Duration.ofSeconds(60));
    return redisTemplate.opsForValue().get(key);
  }

  public String getValue(String key) {
    String value = redisTemplate.opsForValue()  .get(key);
    if (value == null) {
      throw new CoingeckoNotAvailableException(Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    }
    return value;
  }
  
  
}
