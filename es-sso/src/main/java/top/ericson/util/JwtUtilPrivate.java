package top.ericson.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ericson
 * @class JwtUtil
 * @date 2020/04/11 15:15
 * @version 1.0
 * @description JWT签名验证工具(服务器端使用)
 */
@Slf4j
public class JwtUtilPrivate {

    private static final String SECRET = "980305";

    public static String getSECRET() {
        return SECRET;
    }

    private static PrivateKey privateKey;

    private static final String privateKeyString =
        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC8xkrmnAOWyKJDVW4MXDsoTbUii7fVwLINA3YuJGEpgAfxWXm95k5Aht2ZXlUy5kH/qQ/djT4tJse3P6ZoOLmlrK9T1uHpDEUIO0w44dddpyYVw1TQ6EdmkI6f24LNrozAkQQImotFpqg5ERdXZH0Bu/yWT0NmytU7sANuJfE2UDOJSRJo5R3pMu/IOfjbIUUS4XNvUwLAomQTEL2o+/ze5nw1JUlxNrhegreD39F9iNsg5jiW2ZJ7daOoE6uUr09aNJT1ek7P1MSEm4gSQdygcFEc8elG9KpZofGYXMvsFDljm28QpTQtTsQ9B7y3XqZLKfGmuQ4uFUZ5SCtmMu37AgMBAAECggEATeX12pvez0YTz31dYDRfJQ6MBKlrPwW5c61OA1R308xFes7Vcgurlu0QXM2UkuB5s6psJZy3X6J1H6VgYfmmgjt0TkCKEMVDZ1Yw6fgoCzmCedCVG2/+ptBYSpcmrwGsi3pME9DST/epy4LGAwYwn5qXbN9gjPZ+b5b+k2GM3EAezEO0z+wrHBxN8joCLuj4+lbt0ytBWtFLQx3yY79gJ74IeFguyJ5Glj0XBSdlcaNLGDHuHxvwmCcWJSeaYBzNQ268SoCpYz5S5X9XRhkh2gt7sDYpiGP+fLToL6uy+9X87yqDwQ/6YU1msaKd3THnQZrucIygQerE7FWM5p5gYQKBgQDgUR3DJAd6KJ8E74LYG79pZ5bWD2dlpesy5DeNDxPmTZRLE7cp476BQKKKg94SKzVVTFU5CePcn0JYKbFZx9LgbkOdA/9jxSh2md3aq3xTF6420EOYdlcE8TaIqWUKnCDMO7sroLgfLvjTi9WV9AtmSx+7fzdmW7OYAuOty4lIbwKBgQDXcAh24OUbqmUZBFXZ0bduqh5Zk++CPS9Qy7VbJQDGy8zyGh2+6mDcUDz4FRy4TpIa+3sqoVlfMCX7mQ/RXhQBQ7+YoJfY8YixFcV4Vw3bvscfTO2vEplDtikZ58ZRM2ReOfFGUaMjcg0DhRJZp39IJUcPFVcqDPd7u59u/KmBNQKBgHImFSUmgn+RiEp0r3rVs/QpeMJ5mD5LzoJZTEOF9rRtqhcCc2TnthwEDjK6pBlOz5cWfw/FjqTIjnRa0xtC6na25cVTMXVN8SdZaWJLfbqzBD50gNFm9yVWsk+dFAPwK2iCxN6QrOGBf7H3KLtkMyDc/crmFAUMiTnDzqS0PKoVAoGAC7PiQdLlnyMaasl5gGOL+07yZxtKH2EDePgX2eOgryvS7jPM+dnxeQZCyZccAEcEWMLgZeht2mXbKleeLFlX7NmpRrkyhm5YZoYwO+E+t3kdh+2924y/FpaGlQmqFC8efJ/ZI2C74+kGG/LWlFgs6dZcjc0y9u3NY8NFXdoLuyECgYEAhqMr6v8ECeS9DHNE9Ql59eU1YLtWCNW1PXRIx3EqOAf7LXlMc7mJ07J3tObXUWNYharobSA6XThaBwowreUJbMi+Lt23ufbhAEmxa3rp4bzWy8CsRhOr7J+HDBOQJakbFBoC7ZvbL4Dj+j1Nngtl93WmOvtMcYQlAkWexfLVu+0=";

    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    static {
        try {
            byte[] decoded = Base64.getDecoder()
                .decode(privateKeyString);
            privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            log.debug("privateKey:{}", privateKey);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Ericson
     * @date 2020/04/12 15:27
     * @param userId
     * @param password
     * @return jwt
     * @description
     */
    public static String buildJwt(String userId, String username) {
        Date expiration = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String jwt = Jwts.builder()
            /*设置头信息*/
            // 声明加密的算法
            // .setHeaderParam("alg", "RS256")
            // 声明类型
            .setHeaderParam("typ", "JWT")
            // 面向的用户(jwt所面向的用户)
            // .setSubject("user")
            // jwt签发者
            .setIssuer("top.ericson.sso")
            // 接收jwt的一方
            // .setAudience("top.ericson.estore")
            // jwt的签发时间
            .setIssuedAt(new Date())
            // 过期时间戳(jwt的过期时间，这个过期时间必须要大于签发时间)
            .setExpiration(expiration)
            // 定义在什么时间之前，该jwt都是不可用的.
            // .setNotBefore(new Date(0))
            // jwt的唯一身份标识
            .setId(UUID.randomUUID()
                .toString())
            // 私钥和加密算法
            .signWith(privateKey, SignatureAlgorithm.RS256)
            // 添加声明
            .claim("userId", userId)
            .claim("username", username)
            .compact();
        log.debug("jwt:{}", jwt);
        return jwt;
    }

}
