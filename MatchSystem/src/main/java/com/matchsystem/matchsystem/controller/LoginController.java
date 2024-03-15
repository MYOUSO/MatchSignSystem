package com.matchsystem.matchsystem.controller;
import com.matchsystem.matchsystem.pojo.Result;
import com.matchsystem.matchsystem.pojo.User;
import com.matchsystem.matchsystem.service.AccountService;
import com.matchsystem.matchsystem.utils.Code;
import com.matchsystem.matchsystem.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private AccountService accountService;

 @PostMapping("/login")
    public Result login(@RequestBody User user) throws JOSEException {
     log.info("用户登录:{}", user);
     User a = accountService.login(user);
     if(a!= null){
         Map<String, String> cliams =new HashMap<>();
         try{
             cliams.put("ID",a.getId().toString());
             cliams.put("Account",a.getAccount());
             cliams.put("Password",a.getPassword());
             cliams.put("Email",a.getEmail());
             String token = JwtUtils.getToken(cliams);
             log.info(cliams.toString());
             a.setToken(token);
             return Result.success(a);
         }catch (Exception e){
             return Result.error(e.getMessage());
         }
     }
     return a!=null ? Result.success(): Result.error("用户名或密码错误");

    }
    public Result checkToken(@RequestBody User user) throws JOSEException {
        log.info("用户令牌检测:{}", user);
        User a = accountService.login(user);
        if(a!= null){
            Map<String, String> cliams =new HashMap<>();
            try{
                cliams.put("ID",a.getId().toString());
                cliams.put("Account",a.getAccount());
                cliams.put("Password",a.getPassword());
                cliams.put("Email",a.getEmail());
                String token = JwtUtils.getToken(cliams);
                log.info(cliams.toString());
                a.setToken(token);
                return Result.success(a);
            }catch (Exception e){
                return Result.error(e.getMessage());
            }
        }
        return a!=null ? Result.success(): Result.error("用户名或密码错误");

    }
/* @GetMapping("/getVerifyCode")
    public Result getVerifyCodeImg(){
     Code verifyCode =new Code();
     verifyCode.setCodeToken(Code.generateRandomText(5));
     verifyCode.setCodeImg(Code.createCaptchaImage("/getVerifyCode"));
    return Result.success(Code.getCode());
 }*/

}
