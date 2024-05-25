package com.example.credit_score_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private Integer id;
    private String name;
    private Integer extent;
    private Integer population;
}
