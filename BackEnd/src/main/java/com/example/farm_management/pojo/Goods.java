package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private String gid;
    private String gname;
    private Integer stock;
    private Integer sale;
    private Double price;
    private Double score;
    private Integer ratersnum;
}
