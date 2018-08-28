package com.mengyuan.testshiro;

import com.mengyuan.testshiro.model.Permission;
import com.mengyuan.testshiro.model.Role;
import com.mengyuan.testshiro.model.User;
import com.mengyuan.testshiro.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 获取用户 角色/权限 集合，并转成list
     * @param principalCollection PrincipalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从principalCollection中获取user
        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();

        // 声明两个list用来存储user中的permissions权限和角色roles
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();

        //将用户的role集合提取出来，一个用户可能对应多个角色
        Set<Role> roleSet = user.getRoles();

        if (CollectionUtils.isNotEmpty(roleSet)) {
            for (Role role : roleSet) {
                // 将role的名称获取出来加入list
                roleNameList.add(role.getRname());

                // 每个role也对应着多个permission,获取出集合
                Set<Permission> permissionSet = role.getPermissions();

                if (CollectionUtils.isNotEmpty(permissionSet)) {
                    for (Permission permission : permissionSet) {
                        // 将permission的名称获取出来加入list
                        permissionList.add(permission.getName());
                    }
                }

            }
        }
        //声明一个AuthorizationInfo，即认证后存储的权限角色信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //加入上述权限角色信息list
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }

    /**
     * 认证 获取登录信息（用户名密码）
     * @param authenticationToken 使用UsernamePasswordToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 装载一个UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        // 获取username
        String username = usernamePasswordToken.getUsername();
        // 数据库中根据用户名查找user
        User user = userService.findByUsername(username);

        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
