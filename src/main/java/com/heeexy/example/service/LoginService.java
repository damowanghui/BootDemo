package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wph
 * @createTime 2022年02月12日
 * @Description
 */
public interface LoginService {

    JSONObject authLogin(JSONObject jsonObject);

    JSONObject getInfo();

    JSONObject logout();

}
