package com.diet.admin;

import com.alibaba.fastjson.JSON;
import com.diet.admin.secruity.JwtTokenUtil;
import com.diet.admin.secruity.JwtUser;
import com.diet.admin.secruity.JwtTokenUtil;
import com.diet.admin.secruity.JwtUser;

/**
 * @author LiuYu
 * @date 2118/3/24
 */
public class Test {
    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        JwtUser jwtUser = new JwtUser("111", "aaa", "test", null);

        String token = jwtTokenUtil.generateToken(jwtUser);
        System.out.println(token);
        JwtUser user = jwtTokenUtil.getUseFromToken(token);
        System.out.println(JSON.toJSONString(user));
    }

}
