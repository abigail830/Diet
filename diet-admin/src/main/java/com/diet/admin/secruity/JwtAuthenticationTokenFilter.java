package com.diet.admin.secruity;

import com.diet.admin.caches.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiuYu
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private BaseCacheService cacheService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // The part after "Bearer "
            final String authToken = authHeader.substring(tokenHead.length()).trim();
            String cacheKey = jwtTokenUtil.getCacheKeyFromToken(authToken);
            Object cacheVal = cacheService.get(cacheKey);
            String userName = jwtTokenUtil.getUsernameFromToken(authToken);
            logger.info("checking authentication " + userName);

            if (cacheVal != null && userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
                // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                // 本例中，我们直接采用直接验证token是否合法来避免昂贵的数据查询
                // 但需要严格验证的话，你可以通过Spring Security的 @UserDetailsService 进行了数据查询
                /*UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + userName + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }*/

                if (jwtTokenUtil.simpleValidateToken(authToken)) {
                    JwtUser jwtUser = this.jwtTokenUtil.getUseFromToken(authToken);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            jwtUser, null, jwtUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + userName + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
