package com.example.farm_management.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farm {
    private String fid;
    private String fname;
    private String cid;
    private String cname;
    private Integer camount;
    private String wid;
    private Date pdate;
    private Date harvestdate;
    private Integer stage;
    private String posinfo;
}
