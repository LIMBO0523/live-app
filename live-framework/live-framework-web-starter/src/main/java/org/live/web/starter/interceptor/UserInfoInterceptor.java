package org.live.web.starter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.live.common.interfaces.enums.GatewayHeaderEnum;
import org.live.web.starter.constants.RequestConstants;
import org.live.web.starter.context.RequestContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:25
 */
public class UserInfoInterceptor implements HandlerInterceptor{
    //所有web请求来到这里的时候，都要被拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdStr = request.getHeader(GatewayHeaderEnum.USER_LOGIN_ID.getName());
        //可能是走了接口白名单，这里不做取数判断
        if (StringUtils.isEmpty(userIdStr)) {
            return true;
        }
        Long userId = Long.valueOf(userIdStr);
        //把userId放入线程本地变量中
        RequestContext.put(RequestConstants.USER_ID, userId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestContext.clear();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
