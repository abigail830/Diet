package com.diet.admin.secruity;

import com.diet.admin.entity.TbUser;
import com.diet.admin.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author LiuYu
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TbUserService userService;

    @Override
    public JwtUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        TbUser user = userService.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        } else {
            return JwtUserFactory.create(user);
        }
    }

    public JwtUser createUserByUserName(String userName) {
        TbUser user = new TbUser();
        user.setUserName(userName);
        return JwtUserFactory.create(user);
    }
}
