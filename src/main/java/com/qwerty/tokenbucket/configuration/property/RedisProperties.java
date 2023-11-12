package com.qwerty.tokenbucket.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private String hostName;

    private Integer port;
}
