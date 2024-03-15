package com.matchsystem.matchsystem.controller;
import com.matchsystem.matchsystem.pojo.Result;
import com.matchsystem.matchsystem.pojo.User;
import com.matchsystem.matchsystem.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("用户注册:{}", user);
        User a = accountService.UserAccountFree(user);
        User user1 =new User();
        user1.setAccount(user.getAccount());
        user1.setPassword(user.getPassword());
        user1.setDoublePassword(user.getDoublePassword());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        if(a!=null){
            return Result.error("抱歉，该用户名已经被占用");
        }else{
            if(!user1.getPassword().equals(user1.getDoublePassword())){
                return Result.error("您两次输入的密码不一致");
            }
            accountService.add(user);
            return Result.success();
        }

    }
}
