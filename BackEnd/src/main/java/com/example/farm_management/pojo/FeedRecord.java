package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedRecord {
    private String fid;
    private String pid;
    private Date ftime;
}
