package com.diet.admin.secruity;

import com.diet.admin.utils.MD5Util;
import com.diet.admin.utils.RandomGeneratorUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuYu
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERID = "sub_id";
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_NONCE = "nonce";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.rsa.privateKey}")
    private String rsaPrivateKey;

    @Value("${jwt.rsa.publicKey}")
    private String rsaPublicKey;

    @Value("${jwt.expiration}")
    private Long expiration = 604800L;

    /**
     * 从token中获取用户对象
     *
     * @param token
     * @return
     */
    public JwtUser getUseFromToken(String token) {
        JwtUser jwtUser = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            String username = claims.getSubject();
            String userId = String.valueOf(claims.get(CLAIM_KEY_USERID));
            return new JwtUser(userId, username, null, null);
        } catch (Exception e) {
            jwtUser = null;
        }
        return jwtUser;
    }

    /**
     * 获取用户ID
     *
     * @param token
     * @return
     */
    public String getUserIdFromToken(String token) {
        String userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = String.valueOf(claims.get(CLAIM_KEY_USERID));
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 获取用来保存在缓存的key，即MD5(CLAIM_KEY_USERID + CLAIM_KEY_CREATED + CLAIM_KEY_NONCE)
     * @param token
     * @return
     */
    public String getCacheKeyFromToken(String token){
        String result;
        try {
            final Claims claims = getClaimsFromToken(token);
            String userId = String.valueOf(claims.get(CLAIM_KEY_USERID));
            long created = (long) claims.get(CLAIM_KEY_CREATED);
            String nonce = (String) claims.get(CLAIM_KEY_NONCE);
            result = MD5Util.md5Hex(userId + created + nonce);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 获取token创建时间
     *
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获取token超时时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
//                    .setSigningKey(secret)
                    .setSigningKey(getPublicKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private PrivateKey getPrivateKey() {
        try {
            byte[] b1 = Base64.getDecoder().decode(rsaPrivateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PublicKey getPublicKey() {
        try {
            byte[] pubKey = Base64.getDecoder().decode(rsaPublicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * token是否已失效
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(JwtUser jwtUser) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(CLAIM_KEY_USERID, jwtUser.getId());
        claims.put(CLAIM_KEY_USERNAME, jwtUser.getUsername());
        claims.put(CLAIM_KEY_CREATED, System.currentTimeMillis());
        claims.put(CLAIM_KEY_NONCE, RandomGeneratorUtil.generateUUID());
        return generateToken(claims);
    }

    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
//                .signWith(SignatureAlgorithm.HS512, secret)
                .signWith(SignatureAlgorithm.RS512, getPrivateKey())
                .compact();
    }

    /**
     * token能否被刷新
     *
     * @param token
     * @return
     */
    public Boolean canTokenBeRefreshed(String token) {
        Date created = getCreatedDateFromToken(token);
        return (created != null && created.before(new Date())) && !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, System.currentTimeMillis());
            claims.put(CLAIM_KEY_NONCE, RandomGeneratorUtil.generateUUID());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token是否合法
     *
     * @param token
     * @return
     */
    public Boolean simpleValidateToken(String token) {
        Date created = getCreatedDateFromToken(token);
        String userName = getUsernameFromToken(token);
        String userId = getUserIdFromToken(token);
        return (created != null && created.before(new Date()))
                && StringUtils.isNotBlank(userId)
                && StringUtils.isNotBlank(userName)
                && !isTokenExpired(token);
    }
}

