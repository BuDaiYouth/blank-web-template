package xyz.ibudai.template.basic;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import xyz.ibudai.template.basic.util.JwtTokenUtil;

import java.util.concurrent.TimeUnit;

public class TokenTest {

    /**
     * 过期时间
     */
    public static final Long JWT_TTL = TimeUnit.MINUTES.toMillis(5);

    public static void main(String[] args) throws Exception {
        generateJwt();
    }

    private static void generateJwt() throws Exception {
        String userId = "user";
        String token = JwtTokenUtil.createJWT(userId, JWT_TTL);
        System.out.println("Token value: " + token);

        try {
            // Recover token
            Claims claims = JwtTokenUtil.parseJWT(token);
            String data = (String) claims.get("sub");
            System.out.println("Data: " + data);
        } catch (ExpiredJwtException e) {
            System.out.println("User is expired.");
        }
    }
}
