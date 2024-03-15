package com.matchsystem.matchsystem.mapper;
import com.matchsystem.matchsystem.pojo.GameInfo;
import com.matchsystem.matchsystem.pojo.Games;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GamesMapper {


    /**
     *  查询全部部门信息
     * @return
     */
    @Select("select * from games")
    List<Games> list();


    /**
     * 查询记录数
     * @return
     */
    @Select("select count(*) from games")
    public Long count();

    public Long textCount(Integer startIndex , Integer endIndex, String name , int type , LocalDate startTime, LocalDate endTime);


    /**
     * 分页查询获取数据
     * @param startIndex
     * @param endIndex
     * @return
     */
    /*@Select("select * from games limit #{startIndex},#{endIndex}")*/
    public List<Games> pageText(Integer startIndex , Integer endIndex, String name , int type,String orderType,int order , LocalDate startTime, LocalDate endTime);

    /**
     * 查找id
     * @param game
     * @return
     */
    @Select("select id from games where name=#{name} and introduction=#{introduction} and type=#{type} and start_time=#{startTime} and end_time=#{endTime} ")
    int getGameID(Games game);

    /**
     * 根据id删除比赛
     * @param id
     */
    @Delete("delete from games where id=#{id}")
    void deleteById(Integer id);

    @Delete("delete from gameinfo where gameID=#{id}")
    void deleteGameInfoById(Integer id);


    /**
     * 添加比赛
     * @param game
     */
    @Insert("insert into games (name  ,introduction,type,count,start_time,end_time,updateTime,creatTime,creator)VALUES (#{name},#{introduction},#{type},#{count},#{startTime},#{endTime},#{updateTime},#{creatTime},#{creator})")
    void insert(Games game);


    /**
     * 添加比赛信息表
     * @param game
     */
    @Insert("insert into gameinfo(gameID)VALUES (#{id})")
    void insertGameInfo(Games game);





    /**
     * 修改比赛
     * @param game
     *
     */
    @Update("UPDATE games SET name=#{name}, introduction=#{introduction}, type=#{type}, count=#{count} ,start_time=#{startTime},end_time=#{endTime},updateTime=#{updateTime},updatePerson=#{updatePerson} WHERE id=#{id}")
    void updateGame(Games game);


    /**
     *
     * 修改State
     */
    @Update("update games set state = #{newState} where id=#{id}")
    void changeState(Integer id,Integer newState);


    /**
     * 分页查询
     * @param startIndex
     * @param endIndex
     * @return
     */
    @Select("select * from games limit #{startIndex},#{endIndex}")
    public List<Games> page(Integer startIndex , Integer endIndex);

    /**
     * 通过id查询比赛
     * @param id
     * @return
     */
    @Select("select * from games where id=#{id}")
    Games getGameByID(Integer id);

    /**
     * 通过id查询比赛详细信息
     * @param id
     * @return
     */

    /**
     * 查询比赛，通过id
     * @param id
     * @return
     */
    @Select("select * from gameinfo where gameID=#{id}")
    GameInfo getGameInfo(Integer id);


    @Update("UPDATE gameinfo SET topic=#{topic}, information=#{information}, place=#{place}, manager=#{manager} ,beginTime=#{beginTime},endTime=#{endTime},updateTime=#{updateTime},updatePerson=#{updatePerson} WHERE gameID=#{gameID}")
    void updateGameInfo(GameInfo updatedGameInfo);
}
