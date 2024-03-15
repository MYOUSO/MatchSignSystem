package com.matchsystem.matchsystem.controller;
import com.matchsystem.matchsystem.pojo.*;
import com.matchsystem.matchsystem.service.GamesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/games")
@Slf4j
public class GamesCountroller {
    //    private static Logger log= LoggerFactory.getLogger(GamesCountroller.class);
    @Autowired
    private GamesService gamesService;

    /*@RequestMapping(value ="/games",method=RequestMethod.GET)*/
    @GetMapping
    public Result list() {
        log.info("查询全部比赛数据");

        //调用Service查询部门信息
        List<Games> gamesList = gamesService.list();
        return Result.success(gamesList);
    }
    @GetMapping("/gameInfo/{id}")
    public Result gameInfo(@PathVariable Integer id) {
        log.info("查询id:{}比赛数据",id);
        //调用Service查询部门信息
        InfoBean infoBean  = gamesService.getGameInfo(id);
        return Result.success(infoBean);
    }

    @GetMapping("/page/text")
    public Result getTextPageData(

            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            String name, int type,String orderType,int order,
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss") LocalDate startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss") LocalDate endTime) {
        PageBean pageBean;
        try {
            // 调用Service层方法获取分页数据
            pageBean = gamesService.getTextPageData(currentPage, pageSize, name, type, orderType,order,startTime, endTime);
            log.info("分页查询参数{}{}{}{}{}{}", name, type, orderType,order,startTime, endTime);
            // 返回分页数据
            return Result.success(pageBean);
        } catch (Exception e) {
            // 处理异常
            return Result.error("啊咧失败了哦");
        }
    }

    @GetMapping("/page")
    public Result getPageData(

            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            // 调用Service层方法获取分页数据
            PageBean pageBean = gamesService.getPageData(currentPage, pageSize);
            log.info("分页查询");

            // 返回分页数据
            return Result.success(pageBean);
        } catch (Exception e) {
            // 处理异常
            return Result.error("啊咧失败了哦");
        }
    }

    /**
     * 删除值
     *
     * @Return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除比赛:{}", id);
        //删除
        gamesService.delete(id);
        return Result.success();
    }

    /**
     * 增加比赛
     *
     * @Return
     */
    @PostMapping
    public Result add(@RequestBody Games game) {
        log.info("新增比赛:{}", game);
        gamesService.add(game);
        return Result.success();
    }

    /**
     * 修改比赛信息
     *
     * @Return
     */

    @PutMapping("/{id}")
    public Result updateGameById(@PathVariable Integer id, @RequestBody Games updatedGame) {
        log.info("修改比赛:{}", id);
        updatedGame.setId(id);
        gamesService.update(updatedGame);
        return Result.success();
    }

    /**
     *
     *
     * @param updatedGameInfo
     * @return
     */
    @PutMapping("/gameInfo")
    public Result updateGameInfo(@RequestBody GameInfo updatedGameInfo) {
        log.info("修改比赛详细信息:{}", updatedGameInfo.getGameID());
        gamesService.updateGameInfo(updatedGameInfo);
        return Result.success();
    }
}



