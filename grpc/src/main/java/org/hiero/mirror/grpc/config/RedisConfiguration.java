// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.hiero.mirror.common.converter.EntityIdDeserializer;
import org.hiero.mirror.common.converter.EntityIdSerializer;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.topic.TopicMessage;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.micrometer.metrics.autoconfigure.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.micrometer.metrics.autoconfigure.MetricsAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@AutoConfigureBefore(DataRedisAutoConfiguration.class)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class })
@Configuration(proxyBeanMethods = false)
@SuppressWarnings("removal")
class RedisConfiguration {

    @Bean
    RedisSerializer<TopicMessage> redisSerializer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    ReactiveRedisOperations<String, TopicMessage> reactiveRedisOperations(ReactiveRedisConnectionFactory connectionFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
