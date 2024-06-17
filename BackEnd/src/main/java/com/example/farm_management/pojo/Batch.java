package com.example.farm_management.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    private String bid;
    private Date bdate;
    private String originid;
    private String gid;
    private String gname;
    private Integer isdealed;
    private Integer amount;
}
