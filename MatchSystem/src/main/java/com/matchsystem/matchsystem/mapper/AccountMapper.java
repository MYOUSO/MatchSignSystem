package com.matchsystem.matchsystem.mapper;

import com.matchsystem.matchsystem.pojo.Games;
import com.matchsystem.matchsystem.pojo.User;
import com.matchsystem.matchsystem.pojo.UsersGames;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    /**
     *  查询全部账号
     * @return
     */
    @Select("select * from users")
    List<User> list();


    /**
     * 根据id删除比赛
     * @param id
     */
    @Delete("delete from users where id=#{id}")
    void deleteById(Integer id);


    /**
     * 添加成员
     * @param user
     */
    @Insert("insert into users (account,password,email,phone)VALUES (#{account},#{password},#{email},#{phone})")
    void insert(User user);





    /**
     * 修改成员
     * @param id, phone ,email
     *
     */

    void updateAccount(int id ,String phone,String email,String newPassword);



    /**
     * login
     * @param user
     *
     */
    @Select("select * from users where account=#{account} and password=#{password} ")
    User getUserPasswordAndAccount(User user);

    /**
     * register
     * @param user
     * @return
     */

    @Select("select * from users where account=#{account}")
    User getUserAccountFree(User user);


    /**
     *
     * @param userId
     * @param gameId
     */
    @Insert("insert into usergames (UserID,GameID)VALUES (#{userId},#{gameId})")
    void signUpGame(Integer userId, Integer gameId);


    /**
     *
     * @param id
     * @return
     */
    @Select("SELECT games.* FROM games  JOIN usergames ON games.id = usergames.GameID WHERE usergames.UserID =#{id} ")
    List<Games> listSignGames(int id);

    /**
     * 查询是否参加了比赛
     * @param id
     * @param userID
     * @return
     */

   @Select("SELECT * from usergames where UserID=#{userID} and GameID =#{id}")
   UsersGames checkSigned(Integer id, int userID);

    /**
     * 退赛
     * @param userID
     * @param gameID
     */
    @Delete("delete from usergames where UserID=#{userID} and GameID=#{gameID}")
    void leftGame(int userID, int gameID);


    /**
     * 查询比赛的报名上限人数
     * @param id
     * @return
     */
    @Select("select count from games where id=#{id}")
    int checkCount(Integer id);

    /**
     * 查询参赛人数
     * @param id
     * @return
     */
    @Select("select sign_count from games where id=#{id}")
    int checkSignedCount(Integer id);

    /**
     * 更新参赛人数
     * @param id
     * @param signCount
     */
    @Update("update games set sign_count = #{signCount} where id=#{id}")
    void updateGame(Integer id,Integer signCount);

    /**
     * 查询比赛状态
     * @param id
     * @return
     */
    @Select("select state from games where id=#{id}")
    int getGameState(Integer id);
}
