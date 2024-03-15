package com.matchsystem.matchsystem.controller;

import com.matchsystem.matchsystem.pojo.Games;
import com.matchsystem.matchsystem.pojo.User;
import com.matchsystem.matchsystem.pojo.Result;
import com.matchsystem.matchsystem.pojo.UsersGames;
import com.matchsystem.matchsystem.service.AccountService;
import com.matchsystem.matchsystem.service.GamesService;
import com.matchsystem.matchsystem.service.impl.GamesServiceImpl;
import com.matchsystem.matchsystem.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    /*@RequestMapping(value ="/games",method=RequestMethod.GET)*/
    @GetMapping
    public Result list() {


        log.info("查询全部账号数据");
        //调用Service查询部门信息
        List<User> accountsList = accountService.list();
        return Result.success(accountsList);
    }


    /*
     *   删除值
     *   @Return
     *
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除账号:{}", id);
        //删除
        accountService.delete(id);
        return Result.success();
    }

    /**
     * 增加用户
     *
     * @Return
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        log.info("新增账号:{}", user);
        accountService.add(user);
        return Result.success();
    }

    /**
     * 修改比赛信息
     *
     * @Return
     */

    @PutMapping
    public Result updateAccount(@RequestBody User user) {
        User user1 =new User();
        // 使用现有数据填充更新后的属性
        user1 = accountService.login(user);
        if(user1!=null){
            log.info("修改用户:{}", user1.getId());
            accountService.update(user1.getId(),user.getPhone(),user.getEmail(),user.getNewPassword());
            return Result.success();
        }else{
            return Result.error("账号与密码不对应");
        }

    }


    /**
     *
     * @param id
     * @param user
     * 参加比赛
     * @return
     */

    @PostMapping("/{id}")
    public Result signUpGame(@PathVariable Integer id, @RequestBody User user){
        /*//检查人数是否上限了
        boolean flagCount = accountService.ifPeopleExceed(id);
        if(!flagCount){
            return Result.error("抱歉名额已经满了");
            废弃，重复
        }*/
        //检查这个是否在可报名状态下
        int gameState = accountService.ifGameState(id);
        switch (gameState){
            case 0 : break;
            case 1 : return Result.error("抱歉人数已经满了");
            case 2 : return Result.error("比赛报名时间截至");
            case 3 : return Result.error("比赛报名时间还没开始");
            default: log.info("比赛的状态为{}",gameState);
        }
        log.info("获取用户的ID:{}用户的,账号:{}",user.getId(),user.getAccount());
        log.info("得到比赛的id:{}",id);
        UsersGames ug = accountService.checkSigned(id,user.getId());
        if(ug!=null){
            log.info("已经报名了返回error");
            return Result.error("已经报名过了");
        }
        accountService.signUp(id,user);
        accountService.updateGame(id,1);
        return Result.success();
    }


    /**
     * 获取用户报名的比赛
     * @param id
     * @return
     */

    @GetMapping("/listSign")
    public Result listSignUpGame(int id){
        log.info("查找用户ID{}参加的比赛",id);
        List<Games> gamesList =accountService.listSignGame(id);
        return Result.success(gamesList);}

    /**
     * 检查是否参赛
     * @param id
     * @param userID
     * @return
     */


    @GetMapping("/check/{id}")
    public Result checkIfSigned(@PathVariable Integer id ,int userID){
        log.info("查询userID{}名下是否有id:{}的比赛",userID,id);
        UsersGames usersgames = accountService.checkSigned(id,userID);
        if(usersgames!=null){
            log.info("已经报名过这个比赛了");
            return Result.error("已经报名了这个比赛");
        }else{
            return Result.success();
        }
        }

    /**
     * 日你妈退赛！
     * @param id
     * @param userID
     * @return
     */
    @DeleteMapping("/left/{id}")
    public Result leftGame(@PathVariable Integer id ,int userID){
        log.info("查询userID{}名下id:{}的比赛",userID,id);
        accountService.leftGame(userID,id);
        accountService.updateGame(id,-1);
        return Result.success();
    }







    }

