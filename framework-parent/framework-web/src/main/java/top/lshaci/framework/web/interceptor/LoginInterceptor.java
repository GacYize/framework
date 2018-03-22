package top.lshaci.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import top.lshaci.framework.web.enums.ErrorCode;
import top.lshaci.framework.web.model.JsonResponse;
import top.lshaci.framework.web.utils.SessionUserUtils;

/**
 * Login Interceptor
 * 
 * @author lshaci
 * @since 0.0.4
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	
	private String redirectUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("LoginInterceptor: " + request.getRequestURI());
		
		Object loginUser = SessionUserUtils.getUserInSession();
		/*
		 *  Determines whether the user exists, 
		 *  does not exist to return to the login interface, continues to intercept, 
		 *  exists by intercepting, released to the access page.
		 */
		if (loginUser == null) {
			log.warn("Not login.");
			if (isAjaxRequest(request)) {
				log.debug("This request is an ajax request.");
				JsonResponse jsonResponse = new JsonResponse(false, ErrorCode.NOT_LOGIN_EXCEPTION.getMsg());
				jsonResponse.setCode(ErrorCode.NOT_LOGIN_EXCEPTION.getCode());
				log.warn("No login, response json.");
				response.getWriter().write(JSON.toJSONString(jsonResponse));
			} else {
				log.warn("No login, redirect home page.");
				response.sendRedirect(redirectUrl);
			}
			return false;
		}
		
		log.debug("Already login.");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/**
	 * Set the redirect url
	 * 
	 * @param redirectUrl the redirect url
	 * @return this
	 */
	public LoginInterceptor setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}
	
	/**
	 * Determine whether an ajax request is made.
	 * 
	 * @param request the http servlet request
	 * @return if true means this request is ajax request
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		// If it is an ajax request, the request header has (x-requested-with)
		return request.getHeader("x-requested-with") != null && 
				request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
	}
}
