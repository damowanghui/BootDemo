package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wph
 * @createTime 2022年02月18日
 * @Description 文件相关服务
 */
public interface FileService {
    /**
     * 文件列表查询
     * @return 文件列表
     */
    JSONObject fileList(JSONObject jsonObject);

    /**
     * 文件上传
     * @return 文件上传
     */
    JSONObject fileUpload(MultipartFile[] files);

    /**
     * 文件下载
     * @return 文件下载
     */
    JSONObject fileDownload(String fileId, HttpRequest request);

    /**
     * 文件删除
     * @return 文件删除
     */
    JSONObject fileDeleteFile();
}
