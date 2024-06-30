package com.example.farm_management.pojo;


import lombok.*;

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
    private Date pdate;
    private String wid;
    private Date harvestdate;
    private Integer stage;
    private String posinfo;
    private Double longitude;
    private Double latitude;
    private Integer soiltype;
    @Getter
    @Setter
    private Object statistics;
}
