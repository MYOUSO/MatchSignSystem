package com.matchsystem.matchsystem.service.impl;

import com.matchsystem.matchsystem.mapper.AccountMapper;
import com.matchsystem.matchsystem.pojo.Games;
import com.matchsystem.matchsystem.pojo.User;
import com.matchsystem.matchsystem.pojo.UsersGames;
import com.matchsystem.matchsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper ;


    @Override
    public List<User> list() {

        return accountMapper.list();
    }

    @Override
    public void delete(Integer id) {
        accountMapper.deleteById(id);
    }

    @Override
    public void add(User user) {
        accountMapper.insert(user);
    }

    @Override
    public void update(int id,String phone,String email,String newPassword) {accountMapper.updateAccount(id ,phone,email,newPassword); }
    @Override
    public User login(User user) {
       return accountMapper.getUserPasswordAndAccount(user);
    }

    @Override
    public User UserAccountFree(User user) {
        return accountMapper.getUserAccountFree(user);
    }

    @Override
    public void signUp(Integer id, User user) {
        accountMapper.signUpGame(user.getId(),id);
    }

    @Override
    public List<Games> listSignGame(int id) {return accountMapper.listSignGames(id);}

    @Override
    public UsersGames checkSigned(Integer id,int userID) {return accountMapper.checkSigned(id,userID);}

    @Override
    public void leftGame(int userID,int gameID) {accountMapper.leftGame(userID,gameID);}

    @Override
    public boolean ifPeopleExceed(Integer id) {
        int count = accountMapper.checkCount(id);
        int signCount =accountMapper.checkSignedCount(id);
        return signCount < count;
    }

    @Override
    public void updateGame(Integer id,int number) {
        int signCount = accountMapper.checkSignedCount(id);
        signCount+=number;
        accountMapper.updateGame(id,signCount);}

    @Override
    public int ifGameState(Integer id) {return accountMapper.getGameState(id);}




}
