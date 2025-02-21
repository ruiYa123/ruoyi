package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key)
    {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key)
    {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 添加元素到队列
     *
     * @param key 队列键
     * @param value 元素值
     * @param prioritize 是否优先（插队）
     */
    public <T> void addToQueue(final String key, final T value, boolean prioritize) {
        if (prioritize) {
            redisTemplate.opsForList().leftPush(key, value);  // 插队
        } else {
            redisTemplate.opsForList().rightPush(key, value); // 正常添加
        }
    }

    /**
     * 获取并移除队列中的第一个元素
     *
     * @param key 队列键
     * @return 队列中的第一个元素
     */
    public <T> T popFromQueue(final String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    // 通用集合操作方法

    /**
     * 添加元素到集合
     *
     * @param key 集合键
     * @param value 元素值
     */
    public <T> void addToSet(final String key, final T value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 从集合中移除元素
     *
     * @param key 集合键
     * @param value 元素值
     */
    public <T> void removeFromSet(final String key, final T value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 获取集合中的所有元素
     *
     * @param key 集合键
     * @return 集合中的所有元素
     */
    public <T> Set<T> getMembersFromSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取集合中的一个随机元素
     *
     * @param key 集合键
     * @return 集合中的一个随机元素
     */
    public <T> T getRandomMemberFromSet(final String key) {
        return (T) redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 将对象以 JSON 字符串形式存储到 Redis
     *
     * @param key 键
     * @param object 要存储的对象
     */
    public void setObjectAsJson(final String key, final Object object) {
        redisTemplate.opsForValue().set(key, JsonUtil.toJson(object));
    }

    /**
     * 从 Redis 中获取 JSON 字符串并反序列化为对象
     *
     * @param key 键
     * @param clazz 对象类型
     * @return 反序列化后的对象
     */
    public <T> T getObjectFromJson(final String key, Class<T> clazz) {
        return JsonUtil.fromJson((String) redisTemplate.opsForValue().get(key), clazz);
    }

    /**
     * 将对象以 JSON 字符串形式存储到 Redis 哈希表中
     *
     * @param hashKey 哈希表键
     * @param fieldKey 哈希字段键
     * @param object 要存储的对象
     */
    public void putObjectInHash(final String hashKey, final String fieldKey, final Object object) {
        String jsonString = JsonUtil.toJson(object);
        redisTemplate.opsForHash().put(hashKey, fieldKey, jsonString);
    }

    /**
     * 从 Redis 哈希表中获取对象
     *
     * @param hashKey 哈希表键
     * @param fieldKey 哈希字段键
     * @param clazz 对象类型
     * @return 反序列化后的对象
     */
    public <T> T getObjectFromHash(final String hashKey, final String fieldKey, Class<T> clazz) {
        Object jsonString = redisTemplate.opsForHash().get(hashKey, fieldKey);
        if (jsonString != null) {
            return JsonUtil.fromJson(jsonString.toString(), clazz);
        }
        return null;
    }
}
