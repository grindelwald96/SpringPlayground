package com.weather.forecast.Weather.forecast

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate


@Configuration
class RedisConfig {
    @Value("\${spring.redis.host}")
    lateinit var redisHost: String

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val connectionFactory = JedisConnectionFactory()
        connectionFactory.hostName = redisHost
        return connectionFactory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setConnectionFactory(jedisConnectionFactory())
        return redisTemplate
    }
}