package org.arvin;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisTemplate<String,User> redisTemplate;


    @PostMapping("add")
    public User add(  User user){
        redisTemplate.opsForValue().set(String.valueOf(user.getId()),user);
        return user;
    }

    @GetMapping("find/{userId}")
    public User get(@PathVariable("userId") String userId){
        return redisTemplate.opsForValue().get(userId);
    }
}
