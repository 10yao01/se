package com.example.farm_management.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pasture {
    private String pid;
    private String pname;
    private String poultryid;
    private String poultryname;
    private Integer poultrynum;
    private Date starttime;
    private String wid;
    private Date maturetime;
    private Integer stage;
    private Integer longitude;
    private Integer latitude;
    private Double area;
    private String posinfo;
}
