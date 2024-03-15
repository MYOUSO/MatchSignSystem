package com.matchsystem.matchsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfo {
    private String topic;
    private String information;
    private String place;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String manager;
    private LocalDateTime updateTime;
    private String updatePerson;
    private String gameID;
}
