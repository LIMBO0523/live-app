package org.live.account.provider.services;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 12:00
 */
public interface IAccountTokenService {
    /**
     * 创建一个登录token
     *
     * @param userId
     * @return
     */
    String createAndSaveLoginToken(Long userId);

    /**
     * 校验用户token
     *
     * @param tokenKey
     * @return
     */
    Long getUserIdByToken(String tokenKey);

}
