package com.heeexy.example.service;

import com.heeexy.example.dto.session.SessionUserInfo;

/**
 * @author wph
 * @createTime 2022年02月12日
 * @Description
 */
public interface TokenService {

    String generateToken(String username);

    SessionUserInfo getUserInfo();

    SessionUserInfo getUserInfoFromCache(String token);

    void setCache(String token, String username);

    void invalidateToken();

    SessionUserInfo getUserInfoByUsername(String username);
}
