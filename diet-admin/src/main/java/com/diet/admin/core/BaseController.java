package com.diet.admin.core;

import com.diet.admin.caches.BaseCacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller公共组件
 * @author LiuYu
 */
public abstract class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String API = "/api";

    @Value("${jwt.header}")
    protected String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

	@Autowired
	private HttpServletRequest request;

	@Autowired
    protected BaseCacheService cacheService;

	protected String getTokenHeader(){
        return request.getHeader(tokenHeader);
    }

	protected String getTokenFromHeader(){
        String token = getTokenHeader();
        return StringUtils.isBlank(token) ? null : token.substring(tokenHead.length()).trim();
    }
	
	protected String getContextPath(){
		return request.getContextPath();
	}


}
