package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String oid;
    private String customerid;
    private String gid;
    private String gname;
    private Integer amount;
    private Integer score;
    private String eid;
    private Double price;
}
