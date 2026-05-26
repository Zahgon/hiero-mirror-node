// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.hiero.mirror.common.converter.EntityIdDeserializer;
import org.hiero.mirror.common.converter.EntityIdSerializer;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.topic.StreamMessage;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.micrometer.metrics.autoconfigure.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.micrometer.metrics.autoconfigure.MetricsAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@AutoConfigureBefore(DataRedisAutoConfiguration.class)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class })
@Configuration(proxyBeanMethods = false)
@SuppressWarnings("removal")
class RedisConfiguration {

    @Bean
    RedisSerializer<StreamMessage> redisSerializer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    RedisOperations<String, StreamMessage> redisOperations(RedisConnectionFactory redisConnectionFactory, RedisSerializer<StreamMessage> redisSerializer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ReactiveRedisOperations<String, StreamMessage> reactiveRedisOperations(ReactiveRedisConnectionFactory factory, RedisSerializer<StreamMessage> redisSerializer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
