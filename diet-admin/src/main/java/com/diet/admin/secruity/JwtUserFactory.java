package com.diet.admin.secruity;

import com.diet.admin.entity.TbUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiuYu
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(TbUser user) {
        return new JwtUser(
                String.valueOf(user.getId()),
                user.getUserName(),
                user.getPassword(),
                mapToGrantedAuthorities(null)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return new ArrayList<>();
        }
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

