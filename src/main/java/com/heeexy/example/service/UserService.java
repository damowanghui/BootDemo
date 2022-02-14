package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;

/**
 * @author wph
 * @createTime 2022年02月12日
 * @Description
 */
public interface UserService {
    JSONObject listUser(JSONObject jsonObject);

    JSONObject addUser(JSONObject jsonObject);

    JSONObject getAllRoles();

    JSONObject updateUser(JSONObject jsonObject);

    JSONObject listRole();

    JSONObject listAllPermission();

    JSONObject addRole(JSONObject jsonObject);

    JSONObject updateRole(JSONObject jsonObject);

    void dealRoleName(JSONObject paramJson, JSONObject roleInfo);

    void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms);

    void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms);

    JSONObject deleteRole(JSONObject jsonObject);
}
