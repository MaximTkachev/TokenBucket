package com.qwerty.tokenbucket.configuration;

import com.qwerty.tokenbucket.configuration.property.RedisProperties;
import com.qwerty.tokenbucket.configuration.property.TokenBucketProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RedisProperties.class, TokenBucketProperties.class})
public class AppConfiguration {
}
