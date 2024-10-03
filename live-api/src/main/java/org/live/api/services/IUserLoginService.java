package org.live.api.services;

import jakarta.servlet.http.HttpServletResponse;
import org.live.common.interfaces.vo.WebResponseVO;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 11:25
 */
public interface IUserLoginService {

    /**
     * 发送登录验证码
     *
     * @param phone
     * @return
     */
    WebResponseVO sendLoginCode(String phone);

    /**
     * 手机号+验证码登录
     *
     * @param phone
     * @param code
     * @return
     */
    WebResponseVO login(String phone, Integer code, HttpServletResponse response);
}
