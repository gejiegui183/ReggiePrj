package Reggie.controller;


import Reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        return null;
//
//        String orgFileName = file.getOriginalFilename();
//        String suffix = orgFileName.substring(orgFileName.lastIndexOf("."));
//        String fileName = UUID.randomUUID().toString() + suffix;
//
//
//        try {
//            file.transferTo(new File(basePath + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return R.success(fileName);
    }

}
