package com.funcas.pboot.module.upms.auth;

import com.funcas.pboot.common.util.CryptoUtils;
import com.funcas.pboot.common.util.EncodeUtils;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.conf.SpringContextHolder;
import com.funcas.pboot.module.upms.entity.QrLoginMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
public class AuthUtils {

    public static final byte[] DYNAMICKEY = CryptoUtils.generateHmacSha1Key();
    public static final String DYNAMIC_IV = EncodeUtils.encodeBase64(CryptoUtils.generateIV());
    public static final String CHANNEL = "qrLogin";
    public static final String REIDS_KEY_HASH_SIGN = "qrLogin:sign";

    public static String generateTicket(long timestamp, String appid){
        String data = DYNAMIC_IV + ";" + timestamp + ";" + appid;
        String ret = EncodeUtils.encodeBase64(CryptoUtils.hmacSha1(DYNAMICKEY, data.getBytes(Charset.forName("UTF-8"))));
        RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");
        redisTemplate.opsForHash().put(REIDS_KEY_HASH_SIGN, ret, "0");
        return ret;
    }

    public static void removeSign(String sign){
        RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");
        redisTemplate.opsForHash().delete(REIDS_KEY_HASH_SIGN, sign);
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
        RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");
        String ticket = (String)redisTemplate.opsForHash().get(REIDS_KEY_HASH_SIGN, sign);
        // 未使用
        if(StringUtils.isNotEmpty(ticket)) {
            // 校验算法是否是本系统生成
            boolean signVerify = StringUtils.equals(generateTicket(timestamp, appid), sign);
            if(signVerify){
                // 校验是否超时
                return System.currentTimeMillis() - timestamp <= 5 * 60 * 1000;
            }
        }
        return false;
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
