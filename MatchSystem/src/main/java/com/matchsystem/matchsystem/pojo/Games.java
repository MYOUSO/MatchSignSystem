package com.matchsystem.matchsystem.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Games {
    private Integer id ;
    private String name;
    private String introduction;
    private Integer type;
    private Integer count;
    private Integer signCount;
    private LocalDate startTime;
    private LocalDate endTime;
    private LocalDateTime updateTime;
    private LocalDateTime creatTime;
    private String creator;
    private Integer state;
    private String updatePerson;

}
