package io.shaded.campfire.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

/**
 * Provides configuration for Redis and pulls the host name and port from
 * environment variables.
 */
@Configuration
class RedisConfiguration {
  @Bean
  fun redisConnectionFactory(): LettuceConnectionFactory =
    LettuceConnectionFactory(
      RedisStandaloneConfiguration(
        REDIS_HOST,
        REDIS_PORT.toInt()
      )
    )

  companion object {
    val REDIS_HOST = System.getenv("REDIS_HOST") ?: "localhost"
    val REDIS_PORT = System.getenv("REDIS_PORT") ?: "6379"
  }
}
