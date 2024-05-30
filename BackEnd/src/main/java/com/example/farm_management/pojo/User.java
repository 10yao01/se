package com.example.farm_management.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String uid;
    private String pwd;
    private Integer idtype;
    private String name;
    private Integer gender;
    private Integer age;
    private String tel;
}
