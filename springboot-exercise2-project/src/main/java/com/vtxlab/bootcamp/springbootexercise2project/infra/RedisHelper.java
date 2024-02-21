package com.vtxlab.bootcamp.springbootexercise2project.infra;

import java.util.Objects;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisHelper {

  private final RedisTemplate<String, String> redisTemplate;

  private ObjectMapper objectMapper;

  public RedisHelper(RedisTemplate<String, String> redisTemplate,
      ObjectMapper objectMapper) {
    Objects.requireNonNull(redisTemplate);
    Objects.requireNonNull(objectMapper);
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;
  }

  public RedisHelper(RedisConnectionFactory factory,
      ObjectMapper objectMapper) {

    Objects.requireNonNull(objectMapper);
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    // redisTemplate.setHashKeySerializer(null);
    redisTemplate.afterPropertiesSet();
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;

  }

  public <T> T get(String key, Class<T> clazz) throws JsonProcessingException {
    String value = redisTemplate.opsForValue().get(key);
    return objectMapper.readValue(value, clazz);
  }

  public void set(String key, Object value) throws JsonProcessingException {
    String serializedValue = objectMapper.writeValueAsString(value);
    redisTemplate.opsForValue().set(key, serializedValue);
  }

}
