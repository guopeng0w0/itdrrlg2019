package com.itdr.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/*
* 基于guava cache 缓存类
* */
public class TokenCache {

        private static LoadingCache<String,String> localCache= CacheBuilder.newBuilder()
               .initialCapacity(1000)//初始化缓存项为1000
                .maximumSize(10000)//最大缓存项为10000
                .expireAfterAccess(12, TimeUnit.HOURS)//定时回收
                .build(new CacheLoader<String, String>() {
                    //当缓存没有值的时候执行load方法
                    @Override
                    public String load(String s) throws Exception {
                        return "null";
                    }
                });
        private TokenCache() {

        }

        /**
         * 添加缓存
         * @param key
         * @param value
         */
        public static void set(String key, String value){

            localCache.put(key, value);
        }
        /**
         * 根据key取得缓存对象
         * @param key
         * @return
         */
        public static String get(String key){
                String value=null;
            try {
                value=localCache.get(key);
                if ("null".equals(value)){
                    return null;
                }
                return value;
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
