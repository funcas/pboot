package com.funcas.pboot.module.upms.auth;

import com.funcas.pboot.common.util.CryptoUtils;
import com.funcas.pboot.common.util.EncodeUtils;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.conf.SpringContextHolder;
import com.funcas.pboot.module.upms.entity.QrLoginMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
public class AuthUtils {

    private static final byte[] DYNAMICKEY = CryptoUtils.generateHmacSha1Key();
    private static final String DYNAMIC_IV = EncodeUtils.encodeBase64(CryptoUtils.generateIV());
    private static final String CHANNEL = "qrLogin";
    private static final String REDIS_KEY_HASH_SIGN = "qrLogin:sign";
    private static final String UN_USED_FLAG = "0";
    public static final long EXPIRE_SECONDS = 5 * 60;

    public static final String KEY_WECHAT_SESSION = "qrLogin:session";

    public static String generateTicket(long timestamp, String appid){
        String data = DYNAMIC_IV + ";" + timestamp + ";" + appid;
        String ret = EncodeUtils.encodeBase64(CryptoUtils.hmacSha1(DYNAMICKEY, data.getBytes(Charset.forName("UTF-8"))));
        RedisTemplate<String, String> redisTemplate = SpringContextHolder.getBean("redisTemplate");
        redisTemplate.opsForHash().put(REDIS_KEY_HASH_SIGN, ret, UN_USED_FLAG);
        redisTemplate.expire(REDIS_KEY_HASH_SIGN, EXPIRE_SECONDS, TimeUnit.SECONDS);
        return ret;
    }

    public static void removeSign(String sign){
        RedisTemplate<String, String> redisTemplate = SpringContextHolder.getBean("redisTemplate");
        redisTemplate.opsForHash().delete(REDIS_KEY_HASH_SIGN, sign);
    }

    /**
     * 校验签名是否合法
     * 校验二维码是否超时（5分钟）
     * @param sign
     * @param timestamp
     * @param appid
     * @return
     */
    public static boolean verifySign(String sign, long timestamp, String appid){
        RedisTemplate<String,String> redisTemplate = SpringContextHolder.getBean("redisTemplate");
        String flag = (String)redisTemplate.opsForHash().get(REDIS_KEY_HASH_SIGN, sign);
        // 未使用
        return StringUtils.isNotEmpty(flag);
    }

    public static void sendMessage(String ticket, int code, Object ext){
        RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");
        QrLoginMessage qrLoginMessage = new QrLoginMessage();
        qrLoginMessage.setId(ticket);
        qrLoginMessage.setCode(code);
        qrLoginMessage.setExt(ext);
        qrLoginMessage.setTimestamp(System.currentTimeMillis());
        redisTemplate.convertAndSend(CHANNEL, FastJsonUtil.toJson(qrLoginMessage));
    }

}
