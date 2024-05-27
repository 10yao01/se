package com.example.credit_score_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {
    private Integer fid;
    private String name;
    private Short gender;
    private Integer age;
    private String image;
    private String areaname;
    private String password;
    private Integer score;
}
