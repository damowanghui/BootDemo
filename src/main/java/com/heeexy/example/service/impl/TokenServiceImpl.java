package com.heeexy.example.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.heeexy.example.config.exception.CommonJsonException;
import com.heeexy.example.dao.LoginDao;
import com.heeexy.example.dto.session.SessionUserInfo;
import com.heeexy.example.service.TokenService;
import com.heeexy.example.util.RedisUtil;
import com.heeexy.example.util.StringTools;
import com.heeexy.example.util.constants.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wph
 * @createTime 2022年02月12日
 * @Description
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

//    @Autowired
//    Cache<String, SessionUserInfo> cacheMap;
    private Long ExpiredTime = 3600L;

    @SuppressWarnings("all")
    @Autowired
    LoginDao loginDao;
    @Autowired
    RedisUtil redisUtil;

    private final String USER_TOKEN = "user-token:";

    /**
     * 用户登录验证通过后(sso/帐密),生成token,记录用户已登录的状态
     */
    @Override
    public String generateToken(String username) {
        MDC.put("username", username);
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        //设置用户信息缓存
        setCache(token, username);
        return token;
    }

    @Override
    public SessionUserInfo getUserInfo() {
        String token = MDC.get("token");
        return getUserInfoFromCache(token);
    }

    /**
     * 根据token查询用户信息
     * 如果token无效,会抛未登录的异常
     */
    @Override
    public SessionUserInfo getUserInfoFromCache(String token) {
        if (StringTools.isNullOrEmpty(token)) {
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        log.debug("根据token从缓存中查询用户信息,{}", token);
//        SessionUserInfo info = cacheMap.getIfPresent(token);
        SessionUserInfo info = (SessionUserInfo) redisUtil.get(USER_TOKEN + token);
        if (info == null) {
            log.info("没拿到缓存 token={}", token);
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        redisUtil.expire(USER_TOKEN + token, ExpiredTime);
        return info;
    }

    @Override
    public void setCache(String token, String username) {
        SessionUserInfo info = getUserInfoByUsername(username);
        log.info("设置用户信息缓存:token={} , username={}, info={}", token, username, info);
        redisUtil.set(USER_TOKEN + token, info, ExpiredTime);
//        cacheMap.put(token, info);
    }

    /**
     * 退出登录时,将token置为无效
     */
    @Override
    public void invalidateToken() {
        String token = MDC.get("token");
        if (!StringTools.isNullOrEmpty(token)) {
            redisUtil.del(USER_TOKEN + token);
//            cacheMap.invalidate(token);
        }
        log.debug("退出登录,清除缓存:token={}", token);
    }

    @Override
    public SessionUserInfo getUserInfoByUsername(String username) {
        SessionUserInfo userInfo = loginDao.getUserInfo(username);
        if (userInfo.getRoleIds().contains(1)) {
            //管理员,查出全部按钮和权限码
            userInfo.setMenuList(loginDao.getAllMenu());
            userInfo.setPermissionList(loginDao.getAllPermissionCode());
        }
        return userInfo;
    }
}
