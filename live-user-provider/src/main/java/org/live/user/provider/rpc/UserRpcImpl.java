package org.live.user.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.live.user.interfaces.IUserRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService
public class UserRpcImpl implements IUserRpc {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRpcImpl.class);

    @Override
    public String test() {
        LOGGER.info("this is dubbo server test!");
        return "success";
    }
}

