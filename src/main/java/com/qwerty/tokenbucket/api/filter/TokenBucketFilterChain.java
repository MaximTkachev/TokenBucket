package com.qwerty.tokenbucket.api.filter;

import com.qwerty.tokenbucket.configuration.RedisCacheNames;
import com.qwerty.tokenbucket.configuration.property.TokenBucketProperties;
import com.qwerty.tokenbucket.model.CacheData;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "token-bucket", name = "enable", havingValue = "true")
public class TokenBucketFilterChain implements Filter {

    private final TokenBucketProperties properties;

    private final Cache cache;

    public TokenBucketFilterChain(TokenBucketProperties properties, CacheManager cacheManager) {
        this.properties = properties;
        this.cache = cacheManager.getCache(RedisCacheNames.DEFAULT);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String remoteAddress = servletRequest.getRemoteAddr();
        log.info("Request from ip = {} was received", remoteAddress);

        CacheData data = cache.get(remoteAddress, CacheData.class);
        if (data == null) {
            createCache(remoteAddress);
        } else {
            int tokens = data.getTokens();
            if (tokens <= 0) {
                log.warn("Request from ip = {} was blocked", remoteAddress);
                if (servletResponse instanceof HttpServletResponse httpServletResponse) {
                    httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                }

                updateCache(remoteAddress, tokens, System.currentTimeMillis());
                return;
            }

            updateCache(remoteAddress, tokens - 1, System.currentTimeMillis());
        }

        log.info("Request from ip = {} was allowed", remoteAddress);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void createCache(String addr) {
        updateCache(addr, properties.getTokens() - 1, System.currentTimeMillis());
    }

    private void updateCache(String addr, int tokens, long timestamp) {
        CacheData data = CacheData.builder()
                .tokens(tokens)
                .timestamp(timestamp)
                .build();

        cache.put(addr, data);
    }
}
