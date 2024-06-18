package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poultry {
    private String pid;
    private String pname;
    private String pinfo;
    private Integer feedinterval;
}