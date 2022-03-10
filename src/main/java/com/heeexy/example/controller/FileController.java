package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.config.annotation.RequiresPermissions;
import com.heeexy.example.service.FileService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wph
 * @createTime 2022年02月18日
 * @Description 文件相关
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @RequiresPermissions("file:list")
    @GetMapping("/list")
    public JSONObject listFile(HttpServletRequest request){
        return fileService.fileList(CommonUtil.request2Json(request));
    }

    @RequiresPermissions("file:upload")
    @PostMapping("/uploadFile")
    public JSONObject uploadFile(@RequestParam(value = "multipartFiles") MultipartFile[] files){
        return fileService.fileUpload(files);
    }

    @RequiresPermissions("file:download")
    @GetMapping("/downloadFile/{fileId}")
    public JSONObject downloadFile(@PathVariable("fileId") String fileId, HttpResponse response){

        return CommonUtil.successJson();
    }

    @RequiresPermissions("file:delete")
    @GetMapping("/deleteFile")
    public JSONObject deleteFile(){
        return CommonUtil.successJson();
    }

}
