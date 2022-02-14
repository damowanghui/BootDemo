package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.config.exception.CommonJsonException;
import com.heeexy.example.dao.LoginDao;
import com.heeexy.example.dto.session.SessionUserInfo;
import com.heeexy.example.service.LoginService;
import com.heeexy.example.service.TokenService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wph
 * @createTime 2022年02月12日
 * @Description 登录service实现类
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @SuppressWarnings("all")
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private TokenService tokenService;

    /**
     * 登录表单提交
     */
    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        JSONObject user = loginDao.checkUser(username, password);
        if (user == null) {
            throw new CommonJsonException(ErrorEnum.E_10010);
        }
        String token = tokenService.generateToken(username);
        info.put("token", token);
        return CommonUtil.successJson(info);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        SessionUserInfo userInfo = tokenService.getUserInfo();
        log.info(userInfo.toString());
        return CommonUtil.successJson(userInfo);
    }

    /**
     * 退出登录
     */
    @Override
    public JSONObject logout() {
        tokenService.invalidateToken();
        return CommonUtil.successJson();
    }
}
