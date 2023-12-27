package com.master.chat.controller.monitor;

import com.master.chat.common.constant.RedisConstants;
import com.master.chat.framework.web.entity.SysCache;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.StringPoolConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {
    @Autowired
    private RedisTemplate redisTemplate;

    private final static List<SysCache> CACHES = new ArrayList<>();

    {
        CACHES.add(new SysCache(RedisConstants.LOGIN_TOKEN_KEY, "用户信息"));
        CACHES.add(new SysCache(RedisConstants.SYS_CONFIG_KEY, "配置信息"));
        CACHES.add(new SysCache(RedisConstants.SYS_DICT_KEY, "数据字典"));
        CACHES.add(new SysCache(RedisConstants.CAPTCHA_CODE_KEY, "验证码"));
        CACHES.add(new SysCache(RedisConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        CACHES.add(new SysCache(RedisConstants.RATE_LIMIT_KEY, "限流处理"));
    }

    /**
     * 获取监控信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping()
    public ResponseInfo getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return ResponseInfo.success(result);
    }

    /**
     * 获取所有缓存信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/getNames")
    public ResponseInfo cache() {
        return ResponseInfo.success(CACHES);
    }

    /**
     * 获取缓存信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/getKeys/{cacheName}")
    public ResponseInfo getCacheKeys(@PathVariable String cacheName) {
        Set<String> cacheKyes = redisTemplate.keys(cacheName + StringPoolConstant.ASTERISK);
        return ResponseInfo.success(cacheKyes);
    }

    /**
     * 获取缓存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public ResponseInfo getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        String cacheValue = Optional.ofNullable(redisTemplate.opsForValue().get(cacheKey)).map(v -> v.toString()).orElse(StringPoolConstant.EMPTY);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return ResponseInfo.success(sysCache);
    }

    /**
     * 删除缓存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/clearCacheName/{cacheName}")
    public ResponseInfo clearCacheName(@PathVariable String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + StringPoolConstant.ASTERISK);
        redisTemplate.delete(cacheKeys);
        return ResponseInfo.success();
    }

    /**
     * 删除缓存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public ResponseInfo clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return ResponseInfo.success();
    }

    /**
     * 清除所有缓存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/clearCacheAll")
    public ResponseInfo clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys(StringPoolConstant.ASTERISK);
        redisTemplate.delete(cacheKeys);
        return ResponseInfo.success();
    }

}
