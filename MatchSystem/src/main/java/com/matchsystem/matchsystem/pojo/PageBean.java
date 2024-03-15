package com.matchsystem.matchsystem.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageBean {
    private Long total;
    private List<Games> rows;
}
