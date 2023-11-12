package com.qwerty.tokenbucket.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "token-bucket")
public class TokenBucketProperties {

    Integer tokens;

    Long refillPeriodInMillis;
}
