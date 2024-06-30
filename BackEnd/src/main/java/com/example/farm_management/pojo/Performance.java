package com.example.farm_management.pojo;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    private Date date;
    private Integer performance;

}
