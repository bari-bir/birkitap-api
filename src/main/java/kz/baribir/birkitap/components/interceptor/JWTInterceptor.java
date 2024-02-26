package kz.baribir.birkitap.components.interceptor;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.SecurityBase64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> result=new HashMap<>();
        int status_code=401;
        try {
            String authorization = request.getHeader("Authorization");
            String[] authes = authorization.split(" ");
            String token_name=authes[0];
            String token = authes[1];
            Map<String,String> map=null;

                DecodedJWT decodedJWT = jwtUtils.verify(token);
                String payload =new String( SecurityBase64Util.base64_decode(decodedJWT.getPayload()), StandardCharsets.UTF_8);
                map=new ObjectMapper().readValue(payload,Map.class);

            request.setAttribute("jwt_payload", map);
            String user_type=map.get("user_type");
            String request_url= request.getRequestURI();
            if((!"admin".equals(user_type)) && request_url.startsWith("/admin"))
            {
                status_code=403;
                throw new Exception("普通用户无权访问后台！");
            }

            return true;


        }catch (Exception e)
        {
            e.printStackTrace();
            result.put("result_code",status_code);
            result.put("result_msg","Auth Error!");
            response.setStatus(status_code);
            String json_string=   new ObjectMapper().writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json_string);
            response.getWriter().flush();

        }

        return false;
    }
}

