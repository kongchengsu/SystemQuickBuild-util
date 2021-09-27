package com.jtexplorer.redis;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.*;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author shenxinquan
 **/
@SuppressWarnings(value = { "rawtypes"})
@Component
public class RedisCache {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public void setCacheObject(final String key, final Object value) {
        redissonClient.getBucket(key, getBaseCodec(value.getClass().getSimpleName())).set(value);
    }

    /**
     * 获取缓存的基本对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param clazz 返回值的class
     */
    public <T> T getCacheObject(final String key, Class clazz) {
        RBucket<T> rBucket = redissonClient.getBucket(key, getBaseCodec(clazz.getSimpleName()));
        try{
            return rBucket.get();
        }catch (Exception e){
            return null;
        }
    }
    private BaseCodec getBaseCodec(String className){
        switch (className) {
            case "String":
                return StringCodec.INSTANCE;
            case "Double":
                return DoubleCodec.INSTANCE;
            case "Long":
                return LongCodec.INSTANCE;
            case "Integer":
                return IntegerCodec.INSTANCE;
            default:
                return JsonJacksonCodec.INSTANCE;
        }
    }

//    /**
//     * 缓存基本的对象String
//     *
//     * @param key   缓存的键值
//     * @param value 缓存的值
//     */
//    public void setCache(final String key, final String value) {
//        setCacheObject(key,value,StringCodec.INSTANCE);
//    }
//    /**
//     * 缓存基本的对象Double
//     *
//     * @param key   缓存的键值
//     * @param value 缓存的值
//     */
//    public void setCache(final String key, final Double value) {
//        setCacheObject(key,value,DoubleCodec.INSTANCE);
//    }
//    /**
//     * 缓存基本的对象Long
//     *
//     * @param key   缓存的键值
//     * @param value 缓存的值
//     */
//    public void setCache(final String key, final Long value) {
//        setCacheObject(key,value, LongCodec.INSTANCE);
//    }
//    /**
//     * 缓存基本的对象Integer
//     *
//     * @param key   缓存的键值
//     * @param value 缓存的值
//     */
//    public void setCache(final String key, final Integer value) {
//        setCacheObject(key,value, IntegerCodec.INSTANCE);
//    }
//    /**
//     * 缓存类对象
//     *
//     * @param key   缓存的键值
//     * @param value 缓存的值
//     */
//    public void setCache(final String key, final Object value) {
//        setCacheObject(key,value, IntegerCodec.INSTANCE);
//    }

//    /**
//     * 缓存实体类
//     *
//     * @param key      缓存的键值
//     * @param value    缓存的值
//     * @param timeout  时间
//     * @param timeUnit 时间颗粒度
//     */
//    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
//        RBucket<T> result = redissonClient.getBucket(key);
//        result.set(value);
//        result.expire(timeout, timeUnit);
//    }
//
    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }
//
    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        RBucket rBucket = redissonClient.getBucket(key);
        return rBucket.expire(timeout, unit);
    }
//
//    /**
//     * 获得缓存的基本对象。
//     *
//     * @param key 缓存键值
//     * @return 缓存键值对应的数据
//     */
//    public <T> T getCacheObject(final String key) {
//        RBucket<T> rBucket = redissonClient.getBucket(key);
//        return rBucket.get();
//    }
//
    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redissonClient.getBucket(key).delete();
    }
//
//    /* */
//
//    /**
//     * 删除集合对象
//     *
//     * @param collection 多个对象
//     * @return
//     */
//    public void deleteObject(final Collection collection) {
//        RBatch batch = redissonClient.createBatch();
//        collection.forEach(t->{
//            batch.getBucket(t.toString()).deleteAsync();
//        });
//        batch.execute();
//    }
//
//    /**
//     * 缓存List数据
//     *
//     * @param key      缓存的键值
//     * @param dataList 待缓存的List数据
//     * @return 缓存的对象
//     */
//    public <T> boolean setCacheList(final String key, final List<T> dataList) {
//        RList<T> rList = redissonClient.getList(key);
//        return rList.addAll(dataList);
//    }
//
//    /**
//     * 获得缓存的list对象
//     *
//     * @param key 缓存的键值
//     * @return 缓存键值对应的数据
//     */
//    public <T> List<T> getCacheList(final String key) {
//        RList<T> rList = redissonClient.getList(key);
//        return rList.readAll();
//    }
//
//    /**
//     * 缓存Set
//     *
//     * @param key     缓存键值
//     * @param dataSet 缓存的数据
//     * @return 缓存数据的对象
//     */
//    public <T> boolean setCacheSet(final String key, final Set<T> dataSet) {
//        RSet<T> rSet = redissonClient.getSet(key);
//        return rSet.addAll(dataSet);
//    }
//
//    /**
//     * 获得缓存的set
//     *
//     * @param key
//     * @return
//     */
//    public <T> Set<T> getCacheSet(final String key) {
//        RSet<T> rSet = redissonClient.getSet(key);
//        return rSet.readAll();
//    }
//
//    /**
//     * 缓存Map
//     *
//     * @param key
//     * @param dataMap
//     */
//    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
//        if (dataMap != null) {
//            RMap<String, T> rMap = redissonClient.getMap(key);
//            rMap.putAll(dataMap);
//        }
//    }
//
//    /**
//     * 获得缓存的Map
//     *
//     * @param key
//     * @return
//     */
//    public <T> Map<String, T> getCacheMap(final String key) {
//        RMap<String, T> rMap = redissonClient.getMap(key);
//        return rMap.getAll(rMap.keySet());
//    }
//
//    /**
//     * 往Hash中存入数据
//     *
//     * @param key   Redis键
//     * @param hKey  Hash键
//     * @param value 值
//     */
//    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
//        RMap<String, T> rMap = redissonClient.getMap(key);
//        rMap.put(hKey, value);
//    }
//
//    /**
//     * 获取Hash中的数据
//     *
//     * @param key  Redis键
//     * @param hKey Hash键
//     * @return Hash中的对象
//     */
//    public <T> T getCacheMapValue(final String key, final String hKey) {
//        RMap<String, T> rMap = redissonClient.getMap(key);
//        return rMap.get(hKey);
//    }
//
//    /**
//     * 获取多个Hash中的数据
//     *
//     * @param key   Redis键
//     * @param hKeys Hash键集合
//     * @return Hash对象集合
//     */
//    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
//        RListMultimap rListMultimap = redissonClient.getListMultimap(key);
//        return rListMultimap.getAll(hKeys);
//    }
//
//    /**
//     * 获得缓存的基本对象列表
//     *
//     * @param pattern 字符串前缀
//     * @return 对象列表
//     */
//    public Collection<String> keys(final String pattern) {
//        Iterable<String> iterable = redissonClient.getKeys().getKeysByPattern(pattern);
//        return Lists.newArrayList(iterable);
//    }


//    public static void main(String[] args) {
//        // 使用演示，注意RedisCache真实使用时不要用new，要用@Resource注解
//        RedisCache redisCache = new RedisCache();
//        redisCache.setCacheObject("a","a");
//        String a = redisCache.getCacheObject("a",String.class);
//        System.out.println(a);
//
//        redisCache.setCacheObject("b",1);
//        Integer b = redisCache.getCacheObject("b",Integer.class);
//        System.out.println(b);
//
//
//
//        Map<String,String> map = new HashMap<>();
//        map.put("a","a");
//        map.put("b","b");
//        redisCache.setCacheObject("c",map);
//        Map<String,String> c = redisCache.getCacheObject("c",HashMap.class);
//        System.out.println(c);
//
//        Role role = new Role();
//        role.setRoleId(0L);
//        redisCache.setCacheObject("d",role);
//        Role res = redisCache.getCacheObject("d",Role.class);
//        System.out.println(res);
//    }
}
