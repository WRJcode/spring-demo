package org.arvin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import org.arvin.api.CommonResult;
import org.arvin.component.HttpRequestComponent;
import org.arvin.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    HttpRequestComponent httpRequestComponent;

    @GetMapping("admin/code")
    public void getHtoolVerifyCode(HttpServletResponse response) throws IOException {
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        lineCaptcha.createCode();
        System.out.println(lineCaptcha.getCode());
        Jedis jedis = RedisDS.create().getJedis();
        jedis.set(RedisKey.ADMIN_VERIFY_CODE +lineCaptcha.getCode(),lineCaptcha.getCode());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        lineCaptcha.write(response.getOutputStream());
    }

    @GetMapping("admin/logout")
    public CommonResult<Void> adminLogout(){
        String key = RedisKey.ADMIN_USER_INFO +  httpRequestComponent.getAdminUserId().toString();

        //删除缓存
        Jedis jedis = RedisDS.create().getJedis();
        jedis.del(key);
        SecurityContextHolder.clearContext();
        return CommonResult.success(null);
    }

    @GetMapping("mobile/code")
    public String getMobileCode(String mobile){
        //产生四位随机数
        long rand = RandomUtil.randomLong(1000,9999);
        //调用手机服务商接口

        Jedis jedis = RedisDS.create().getJedis();
        jedis.set(RedisKey.ADMIN_VERIFY_CODE +mobile,String.valueOf(rand));
        return String.valueOf(rand);

        //获取手机验证码，这里做了模拟，通过接口返回
    }

    @GetMapping("mobile/logou")
    public CommonResult<Void> mobileLogout(){
        String key = RedisKey.MOBILE_USER_INFO + httpRequestComponent.getAdminUserId().toString();

        //删除缓存即可
        Jedis jedis = RedisDS.create().getJedis();
        jedis.del(key);
        SecurityContextHolder.clearContext();
        return CommonResult.success(null);
    }

}
