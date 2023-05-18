package com.johnie.kafkademo.redis;

import com.johnie.kafkademo.util.SpringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * By default, it uses Java serialization for its objects (through
 * {@link JdkSerializationRedisSerializer})
 */
public class ObjectRedisUtil {
    private ObjectRedisUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static RedisTemplate<String, Object> getRedisTemplate() {
        return SpringUtil.getBean("objectRedisTemplate", RedisTemplate.class);
    }

    /**
     * @param key
     * @param object 需要实现Serializable接口
     */
    public static void set(String key, Object object) {
        getRedisTemplate().opsForValue().set(key, object);
    }

    public static void set(String key, Object object, long timeout) {
        getRedisTemplate().opsForValue().set(key, object);
        getRedisTemplate().expire(key, timeout, TimeUnit.MINUTES);
    }

    public static <T> T get(String key) {
        return (T) getRedisTemplate().opsForValue().get(key);
    }

    public static boolean checkExist(String key) {
        return Boolean.TRUE.equals(getRedisTemplate().hasKey(key));
    }

    public static boolean delete(String key) {
        return Boolean.TRUE.equals(getRedisTemplate().delete(key));
    }

    public static void expire(String key, long timeout) {
        getRedisTemplate().expire(key, timeout, TimeUnit.MINUTES);
    }

//	/**
//	 * 批量删除指定公司编号下面所有的以curr_login_user_of_part_obj_和curr_login_user_of_part_obj_开头的键值的记录
//	 *
//	 * @param companyNo
//	 */
//	public static void deleteAllUsersByCompanyNo(String companyNo) {
//		// 匹配存储为对象的redis key，第一个通配符代表键值前面自动添加的序列化二进制字符(像这样：\xAC\xED\x00\x05t\x00)，
//		// 第二个*通配part和complete，第三个*通配该公司下已经登录的所有的手机号，第四个*通配所有的roleType
//		String pattern = "*curr_login_user_of_*_obj_" + "*_" + companyNo + "_" + "*";
//		RedisSharedUtil.batchDelete(pattern, getRedisTemplate());
//	}
//
//	public static void deleteAllUsersByCompanyNoAndTel(String companyNo, String telephone) {
//		// 匹配存储为对象的redis key，第一个通配符代表键值前面自动添加的序列化二进制字符(像这样：\xAC\xED\x00\x05t\x00)，
//		// 第二个*通配part和complete，第三个*通配所有的roleType
//		String pattern = "*curr_login_user_of_*_obj_" + telephone + "_" + companyNo + "_" + "*";
//		RedisSharedUtil.batchDelete(pattern, getRedisTemplate());
//	}

}
