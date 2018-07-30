package com.diet.admin.service;


import com.diet.admin.entity.TbUser;

public interface AuthService {
    TbUser register(String userName, String password);

    String login(String userName, String password);

    void logout(String token);

    String refresh(String oldToken);
}
