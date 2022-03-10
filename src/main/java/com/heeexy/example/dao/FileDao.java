package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.data.entity.SysFileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wph
 * @createTime 2022年02月18日
 * @Description
 */
@Mapper
public interface FileDao extends BaseMapper<SysFileEntity> {
    List<JSONObject> listFile(JSONObject jsonObject);
}
