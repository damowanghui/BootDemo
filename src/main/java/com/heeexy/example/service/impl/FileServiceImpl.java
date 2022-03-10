package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.config.exception.CommonJsonException;
import com.heeexy.example.dao.FileDao;
import com.heeexy.example.data.entity.SysFileEntity;
import com.heeexy.example.service.FileService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sun.deploy.net.HttpResponse;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wph
 * @createTime 2022年02月18日
 * @Description
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @SuppressWarnings("all")
    @Autowired
    FileDao fileDao;

    @Autowired
    GridFsTemplate gridFsTemplate;



    @Override
    public JSONObject fileList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = fileDao.selectCount(null);
        List<JSONObject> list = fileDao.listFile(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject fileUpload(MultipartFile[] files) {
        if (ObjectUtils.isEmpty(files)){
            throw new CommonJsonException(ErrorEnum.E_40001);
        }
        String username = MDC.get("username");
        for (MultipartFile file : files) {
            // 校验文件类型
            String type = file.getContentType();

            // 获取文件后缀
            String fileExt = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            // 重构文件名称
            String fileName = generateSuffix() + "." + fileExt;
            try {
                String mongoId = uploadToGridFS(file.getInputStream(), fileName, type);
                //给数据库中添加数据
                SysFileEntity fileEntity = new SysFileEntity();
                fileEntity.setId(mongoId);
                fileEntity.setFileName(fileName);
                fileEntity.setCreateTime(new Date(System.currentTimeMillis()));
                fileEntity.setCreateUser(username);
                fileDao.insert(fileEntity);
            } catch (Exception e) {
                log.error(e.getMessage() + e.getLocalizedMessage());
                return CommonUtil.errorJson(ErrorEnum.E_40002);
            }

        }

        return CommonUtil.successJson();
    }

    @Override
    public JSONObject fileDownload(String fileId, HttpRequest request) {
        //根据id查询文件
        Query query = Query.query(Criteria.where("_id").is(fileId));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        if (ObjectUtils.isEmpty(gridFSFile)) {
            return CommonUtil.errorJson(ErrorEnum.E_40003);
        }


        return null;
    }

    @Override
    public JSONObject fileDeleteFile() {
        return null;
    }

    /**
     * 将文件上传到GridFS中
     * @param in 文件流
     * @param contentType 文件类型
     */
    private String uploadToGridFS(InputStream in, String fileName, String contentType){
        String id = gridFsTemplate.store(in, fileName, contentType).toString();
        return id;
    }
    /**
     * 产生文件名
     *
     * @return 文件名
     */
    private static String generateSuffix() {
        // 获得当前时间
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 转换为字符串
        String formatDate = format.format(new Date());
        // 随机生成文件编号
        int random = new Random().nextInt(100000);
        return formatDate + random;
    }
}
