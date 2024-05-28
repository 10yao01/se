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
    private String poultrynum;
    private Date stime;
    private String wid;
    private Date mtime;
    private Integer stage;
    private String pos;
}
