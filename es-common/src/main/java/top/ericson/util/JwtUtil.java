package top.ericson.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.User;
import java.util.Base64;

/**
 * @author Ericson
 * @class JwtUtil
 * @date 2020/04/11 15:15
 * @version 1.0
 * @description JWT签名验证工具(微服务端使用)
 */
@Lazy
@Slf4j
@Configuration
public class JwtUtil {

    private static final String publicKeyString =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvMZK5pwDlsiiQ1VuDFw7KE21Iou31cCyDQN2LiRhKYAH8Vl5veZOQIbdmV5VMuZB/6kP3Y0+LSbHtz+maDi5payvU9bh6QxFCDtMOOHXXacmFcNU0OhHZpCOn9uCza6MwJEECJqLRaaoOREXV2R9Abv8lk9DZsrVO7ADbiXxNlAziUkSaOUd6TLvyDn42yFFEuFzb1MCwKJkExC9qPv83uZ8NSVJcTa4XoK3g9/RfYjbIOY4ltmSe3WjqBOrlK9PWjSU9XpOz9TEhJuIEkHcoHBRHPHpRvSqWaHxmFzL7BQ5Y5tvEKU0LU7EPQe8t16mSynxprkOLhVGeUgrZjLt+wIDAQAB";

    private static PublicKey publicKey;

    static {
        try {
            byte[] decoded = Base64.getDecoder()
                .decode(publicKeyString);
            publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
            log.debug("publicKey:{}", publicKey);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static JwtUtil build() {
        return new JwtUtil();
    }

    public Jws<Claims> cheakJwt(String jwt) {
        /*验证*/
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwt);
        } catch (JwtException e) {
            e.printStackTrace();
            log.debug("验证失败");
            return null;
        }
        return jws;
    }

    public User perseJwt(Jws<Claims> jws) {
        /*解析*/
        Integer userId = null;
        String username = null;
        try {
            Claims body = jws.getBody();
            userId = (Integer)body.get("userId");
            username = (String)body.get("username");
            log.debug("userId:{}", userId);
            log.debug("username:{}", username);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("解析失败");
            return null;
        }

        User user = new User().setUserId(userId)
            .setUsername(username);
        return user;
    }

}
