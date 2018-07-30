package com.diet.admin.service.impl;

import com.diet.admin.secruity.JwtTokenUtil;
import com.diet.admin.secruity.JwtUser;
import com.diet.admin.secruity.JwtUserDetailsServiceImpl;
import com.diet.admin.service.AuthService;
import com.diet.admin.caches.BaseCacheService;
import com.diet.admin.entity.TbUser;
import com.diet.admin.secruity.JwtTokenUtil;
import com.diet.admin.secruity.JwtUser;
import com.diet.admin.secruity.JwtUserDetailsServiceImpl;
import com.diet.admin.service.AuthService;
import com.diet.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author LiuYu
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BaseCacheService cacheService;

    @Autowired
    private TbUserService userService;

    @Override
    public TbUser register(final String userName, final String password) {
        if (userService.findByUserName(userName) != null) {
            return null;
        }
        TbUser tbUser = new TbUser();
        tbUser.setUserName(userName);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        tbUser.setPassword(encoder.encode(password));
        tbUser.setLastPasswordResetDate(new Date());
        tbUser.setNickName(tbUser.getUserName());
        tbUser.setLastLoginDate(tbUser.getLastPasswordResetDate());
        userService.insert(tbUser);
        return tbUser;
    }

    @Override
    public String login(String userName, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final JwtUser jwtUser = jwtUserDetailsService.loadUserByUsername(userName);
        final String token = jwtTokenUtil.generateToken(jwtUser);
        String cacheValue = jwtTokenUtil.getCacheKeyFromToken(token);
        cacheService.put(cacheValue, cacheValue, expiration);
        return token;
    }

    @Override
    public void logout(String token) {
        if(StringUtils.isNotBlank(token)) {
            cacheService.remove(jwtTokenUtil.getCacheKeyFromToken(token));
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String result = jwtTokenUtil.refreshToken(token);
            String cacheValue = jwtTokenUtil.getCacheKeyFromToken(result);
            cacheService.put(cacheValue, cacheValue, expiration);
            return result;
        }
        return null;
    }
}
