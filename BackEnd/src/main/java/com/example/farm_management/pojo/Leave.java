package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
    private String eid;
    private Date startdate;
    private Date backdate;
    private Integer isdealed;
}
