package com.matchsystem.matchsystem.service.impl;

import com.matchsystem.matchsystem.mapper.GamesMapper;
import com.matchsystem.matchsystem.pojo.GameInfo;
import com.matchsystem.matchsystem.pojo.Games;
import com.matchsystem.matchsystem.pojo.InfoBean;
import com.matchsystem.matchsystem.pojo.PageBean;
import com.matchsystem.matchsystem.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class GamesServiceImpl implements GamesService {


    @Autowired
    private GamesMapper gamesMapper;

    @Override
    public List<Games> list() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Games> tempList = gamesMapper.list();
        for (Games games : tempList) {
            //假如现在的时间在比赛结束日期之后，说明比赛报名结束了
            if (currentTime.isAfter(games.getEndTime().atStartOfDay())) {
                games.setState(2);
                gamesMapper.changeState(games.getId(), 2);
                //假如现在时间在比赛报名开始日期之前，说明还没开放
            } else if (currentTime.isBefore(games.getStartTime().atStartOfDay())) {
                games.setState(3);
                gamesMapper.changeState(games.getId(), 3);
            } else {//假如参赛人数大过需要人数，说明满了
                if (games.getCount() <= games.getSignCount()) {
                    games.setState(1);
                    gamesMapper.changeState(games.getId(), 1);
                }else{//什么都没，说明这个比赛很健康
                    games.setState(0);
                    gamesMapper.changeState(games.getId(), 0);
                }
            }

        }
        return tempList;
    }

    @Override
    public void delete(Integer id) {
        gamesMapper.deleteById(id);
        gamesMapper.deleteGameInfoById(id);
    }

    @Override
    public void add(Games game) {
        LocalDateTime currentTime = LocalDateTime.now();
        game.setUpdateTime(currentTime);
        game.setCreatTime(currentTime);
        gamesMapper.insert(game);
        game.setId(gamesMapper.getGameID(game));
        gamesMapper.insertGameInfo(game);
    }

    @Override
    public void update(Games game) {
        LocalDateTime currentTime = LocalDateTime.now();
        game.setUpdateTime(currentTime);
        game.setCreatTime(currentTime);
        gamesMapper.updateGame(game);
    }



    @Override
    public PageBean getTextPageData(int currentPage, int pageSize,String name , int type, String orderType ,int order, LocalDate startTime,LocalDate endTime) {
        int start = (currentPage-1)*pageSize;
        Long count = gamesMapper.textCount(start,pageSize,name,type,startTime,endTime);
        List<Games> gameData = gamesMapper.pageText(start,pageSize,name,type,orderType,order,startTime,endTime);
        PageBean pageBean = new PageBean(count,gameData);
        return pageBean;
    }


    @Override
    public PageBean getPageData(int currentPage, int pageSize ) {
        Long count = gamesMapper.count();
        int start = (currentPage-1)*pageSize;
        List<Games> gameData = gamesMapper.page(start,pageSize);
        LocalDateTime currentTime = LocalDateTime.now();
        for (Games games : gameData) {
            //假如现在的时间在比赛结束日期之后，说明比赛报名结束了
            if (currentTime.isAfter(games.getEndTime().atStartOfDay())) {
                games.setState(2);
                gamesMapper.changeState(games.getId(), 2);
                //假如现在时间在比赛报名开始日期之前，说明还没开放
            } else if (currentTime.isBefore(games.getStartTime().atStartOfDay())) {
                games.setState(3);
                gamesMapper.changeState(games.getId(), 3);
            } else {//假如参赛人数大过需要人数，说明满了
                if (games.getCount() <= games.getSignCount()) {
                    games.setState(1);
                    gamesMapper.changeState(games.getId(), 1);
                }else{//什么都没，说明这个比赛很健康
                    games.setState(0);
                    gamesMapper.changeState(games.getId(), 0);
                }
            }

        }
        PageBean pageBean = new PageBean(count,gameData);
        return pageBean;
    }

    @Override
    public InfoBean getGameInfo(Integer id) {
        InfoBean infoBean =new InfoBean();
        Games game = gamesMapper.getGameByID(id);
        GameInfo gameInfo = gamesMapper.getGameInfo(id);
        infoBean.setGame(game);
        infoBean.setGameInfo(gameInfo);
        return infoBean;
    }

    @Override
    public void updateGameInfo(GameInfo updatedGameInfo) {
        LocalDateTime currentTime = LocalDateTime.now();
        updatedGameInfo.setUpdateTime(currentTime);
        gamesMapper.updateGameInfo(updatedGameInfo);
    }


    ;
    }



