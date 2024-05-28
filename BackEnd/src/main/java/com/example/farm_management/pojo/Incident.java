package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incident {
    private Integer Iid;
    private String title;
    private Integer fid;
    private String name;
    private String description;
    private Short classes;
    private Short degree;
    private Short isdeal;
}
