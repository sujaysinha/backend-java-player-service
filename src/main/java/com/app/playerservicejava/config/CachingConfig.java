package com.app.playerservicejava.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//Currently commented out as we should have one Cache Manager only, either Redis or ConcurrentMapCacheManager
//Also the name of Bean was coinciding


@Configuration
@EnableCaching //For enabling caching we need to add this annotation to any @Configuration class
public class CachingConfig {

  @Bean
    public CacheManager cacheManager() { //Seems the name of function does not matter, but it is a good practice to name it cacheManager
        //Also the name of function determines the name of the cache manager bean
        return new ConcurrentMapCacheManager("addresses", "players", "player");
    }
}