package org.arvin.filter;

import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import org.arvin.constant.RedisKey;
import org.arvin.constant.TokenHeader;
import org.arvin.dto.SysUserDTO;
import org.arvin.token.MobileAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class MobileAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token) || !token.startsWith(TokenHeader.MOBILE_TOKEN_PREFIX)){
            //如果不存在和前缀不是TokenHeader.MOBILE_TOKEN_PREFIX，就放行到下一个过滤器
            chain.doFilter(request, response);
            SecurityContextHolder.clearContext();
            return;
        }
        //获取真实的token(去掉前缀)
        String authToken = token.substring(TokenHeader.MOBILE_TOKEN_PREFIX.length());

        //解析token
        JWT jwt;
        String code = null;
        try{
            jwt = JWTUtil.parseToken(authToken);
        }catch (Exception e){
            chain.doFilter(request, response);
            return;
        }
        if (!Objects.isNull(jwt.getPayload("uid"))) {
            code = jwt.getPayload("uid").toString();
        }
        if (!StringUtils.hasLength(code)){
            chain.doFilter(request, response);
            return;
        }

        Jedis jedis = RedisDS.create().getJedis();
        //单点登录
        if (!authToken.equals(jedis.get(RedisKey.MOBILE_USER_TOKEN+code))){
            chain.doFilter(request, response);
            return;
        }

        //从缓存中获取用户信息
        String key = RedisKey.MOBILE_USER_INFO + code;
        if (!jedis.exists(key)){
            chain.doFilter(request, response);
            return;
        }

        Object userObj = jedis.get(key);
        if (Objects.isNull(userObj)){
            chain.doFilter(request, response);
            return;
        }

        //保存手机号验证的相关信息
        SysUserDTO user = JSONObject.parseObject(userObj.toString(), SysUserDTO.class);
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
}
