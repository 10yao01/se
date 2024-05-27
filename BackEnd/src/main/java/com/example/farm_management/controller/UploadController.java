package com.example.farm_management.controller;


import com.example.farm_management.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {


    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传，文件名：{}",image.getOriginalFilename());

        String originalFilename = image.getOriginalFilename();

        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFilename = UUID.randomUUID().toString()+extname;

        log.info("新的文件名：{}",newFilename);


        image.transferTo(new File("E:\\大二下\\农业信息化应用开发实训2\\image"+newFilename));

        return Result.success("E:\\大二下\\农业信息化应用开发实训2\\image"+newFilename);
    }
}
