package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wph
 * @createTime 2022年02月12日
 * @Description
 */
public interface ArticleService {

    JSONObject addArticle(JSONObject jsonObject);

    JSONObject listArticle(JSONObject jsonObject);

    JSONObject updateArticle(JSONObject jsonObject);

}
