package kz.baribir.birkitap.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token_expire_second}")
    private int token_expire_second;

    public String getToken(Map<String,String> payload)
    {
        try {
            JWTCreator.Builder builder= JWT.create();
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.SECOND,token_expire_second);
            if(payload!=null)
            {
                payload.forEach((k,v)->{
                    builder.withClaim(k,v);
                });
            }

            String token=  builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(secret));
            return token;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DecodedJWT verify(String token)
    {
        if(token==null)
            return null;
        DecodedJWT decodedJWT= JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        return decodedJWT;
    }

    public TokenInfo getTokenInfo(String token){
        if(token==null)
            return null;
        DecodedJWT decodedJWT= verify(token);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUuid(claimToString(decodedJWT, "uuid"));
        tokenInfo.setUsername(claimToString(decodedJWT, "username"));
        tokenInfo.setType(claimToString(decodedJWT, "type"));
        return tokenInfo;
    }

    public TokenInfo getTokenInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        if(token==null)
            return null;
        DecodedJWT decodedJWT= verify(token);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUuid(claimToString(decodedJWT, "uid"));
        tokenInfo.setUsername(claimToString(decodedJWT, "username"));
        tokenInfo.setType(claimToString(decodedJWT, "type"));
        return tokenInfo;
    }

    private String claimToString(DecodedJWT decodedJWT, String key){
        String result = null;
        if(decodedJWT.getClaim(key) != null)
            result = decodedJWT.getClaim(key).asString();
        return result;
    }
}
