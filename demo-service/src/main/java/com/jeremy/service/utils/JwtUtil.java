package com.jeremy.service.utils;

import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 13:41
 * @Description: git 工具包
 */
public class JwtUtil {
    private static final String SECRET = "ksksnield930282jdjdkd";

    /**
     * token 加密
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<?,?> keyMap) throws Exception {
        if (keyMap == null || keyMap.size() == 0){
            throw new Exception("error");
        }
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("keyMap",keyMap);
        String jwt = Jwts.builder()
                //传入对象
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
        return jwt;
    }

    /**
     * 解密
     * @param token
     * @param ip
     * @return
     * @throws BusinessException
     */
    public static Map<String,Object> validate(String token,String ip) throws BusinessException {
        if (StringUtils.isBlank(token)){
            throw new BusinessException(ResponseCodes.TOKENNULL);
        }
        try {
            //token解析
            Map<String,Object> body = Jwts.parser()
                    //传入密钥
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            //验证无异常后刷新token过期时间
            HashMap<String,Object> params = (HashMap<String, Object>) body.get("keyMap");
            String tokenIp = params.get("ip").toString();
            if (StringUtils.isBlank(tokenIp) || !ip.equals(tokenIp)){
                throw new BusinessException(ResponseCodes.TOKENOVERDUE);
            }
            return params;

        }catch (SignatureException e){
            throw new BusinessException(ResponseCodes.TOKENERROR);
        } catch (MalformedJwtException e) {
            // token解析错误
            throw new BusinessException(ResponseCodes.TOKENERROR);
        } catch (ExpiredJwtException e) {
            // jwt已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
            throw new BusinessException(ResponseCodes.TOKENOVERDUE);
        }

    }


}
