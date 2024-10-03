package org.live.account.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.live.account.interfaces.IAccountTokenRpc;
import org.live.account.provider.services.IAccountTokenService;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 11:59
 */
@DubboService
public class AccountTokenRpcImpl implements IAccountTokenRpc {
    @Resource
    private IAccountTokenService accountTokenService;

    @Override
    public String createAndSaveLoginToken(Long userId) {
        return accountTokenService.createAndSaveLoginToken(userId);
    }

    @Override
    public Long getUserIdByToken(String tokenKey) {
        return accountTokenService.getUserIdByToken(tokenKey);
    }

}
