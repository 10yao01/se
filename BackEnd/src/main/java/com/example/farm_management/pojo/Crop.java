package com.example.farm_management.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crop {
    private String cid;
    private String cname;
    private String cinfo;
    private Integer wtime;
    private Integer ftime;
}
