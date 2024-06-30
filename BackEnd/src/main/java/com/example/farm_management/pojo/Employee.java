package com.example.farm_management.pojo;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String eid;
    private String ename;
    @Getter
    @Setter
    private Object performance;
    private String tel;
    private Integer salary;
    private Integer isgot;
}
