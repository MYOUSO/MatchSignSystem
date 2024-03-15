package com.matchsystem.matchsystem.service;

import com.matchsystem.matchsystem.pojo.Games;
import com.matchsystem.matchsystem.pojo.User;
import com.matchsystem.matchsystem.pojo.UsersGames;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 展示账号
 * @Return
 */
@Service
public interface AccountService {
    List<User> list();


    /**
     * 根据id删人
     * @param id
     */
    void delete(Integer id);


    /**
     * 增加账号
     * @param user
     */
    void add(User user);


    /**
     * 根据账号删人
     * @param user
     */
    void update(int id,String phone,String email,String newPassword);


    /**
     *
     *
     * 用户登录
     * @param user
     */
    User login(User user);


    /**
     *
     *
     * 用户注册
     * @param user
     */
    User UserAccountFree(User user);


    /**
     * 报名比赛
     * @param id,user
     */
    void signUp(Integer id ,User user);


    /**
     * 查询报名参加的比赛
     * @param id
     * @return
     */
    List<Games> listSignGame(int id);

    UsersGames checkSigned(Integer id ,int userID);

    void leftGame(int userID,int gameID);

    boolean ifPeopleExceed(Integer id);

    void updateGame(Integer id,int number);

    int ifGameState(Integer id);
}
