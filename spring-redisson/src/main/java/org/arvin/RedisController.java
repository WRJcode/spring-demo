package org.arvin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    RedissonUtil redissonUtil;

    @GetMapping("setlock")
    public String setLock(){
        redissonUtil.lock("lock_key");
        try{
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        redissonUtil.unlock("lock_key");
        return "lock successfully!";
    }
}
