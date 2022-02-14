package com.heeexy.example.dto.session;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 保存在session中的用户信息
 * @author wangdada
 */
@Data
public class SessionUserInfo implements Serializable {
    private static final long serialVersionUID = 7375266691129231192L;
    private int userId;
    private String username;
    private String nickname;
    private List<Integer> roleIds;
    private Set<String> menuList;
    private Set<String> permissionList;
}
