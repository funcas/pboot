package com.funcas.pboot.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月22日
 */
public class JwtUtil {

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 3600*2*1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            throw exception;
//            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param subject 主题
     * @param secret 用户的密码
     * @param claim 私有属性
     * @return 加密的token
     */
    public static String sign(String subject, String secret, Map<String,String> claim) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create()
                .withSubject(subject)
                .withExpiresAt(date);
        if(claim != null && !claim.isEmpty()) {
            for(String key : claim.keySet()) {
                builder.withClaim(key, claim.get(key));
            }
        }

        return builder.sign(algorithm);
    }
}
