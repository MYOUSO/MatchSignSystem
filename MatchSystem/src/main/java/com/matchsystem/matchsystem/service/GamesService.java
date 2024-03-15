package com.matchsystem.matchsystem.service;

import com.matchsystem.matchsystem.pojo.GameInfo;
import com.matchsystem.matchsystem.pojo.Games;
import com.matchsystem.matchsystem.pojo.InfoBean;
import com.matchsystem.matchsystem.pojo.PageBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface GamesService {
    /*
     *查询所有比赛信息
     * @return
     */
    List<Games> list();


    /*
    删除比赛

     */
    void delete(Integer id);

    /**
     * 新增比赛
     * @param game
     */
    void add(Games game);



    /**
     *修改比赛
     *
     * @param game
     */
    void update(Games game);


     /**
     * 有数据的分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean getTextPageData(int currentPage, int pageSize ,String name , int type ,String orderType ,int order,LocalDate startTime, LocalDate endTime);





    /**
     * 有数据的分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean getPageData(int currentPage, int pageSize );


    InfoBean getGameInfo(Integer id);

    void updateGameInfo(GameInfo updatedGameInfo);
}
