package org.live.id.generate.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.live.id.generate.interfaces.IdBuilderRpc;
import org.live.id.generate.provider.service.IdGenerateService;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 21:49
 */
@DubboService
public class IdBuilderRpcImpl implements IdBuilderRpc {
    @Resource
    private IdGenerateService idGenerateService;

    @Override
    public Long increaseSeqId(int code) {
        return idGenerateService.getSeqId(code);
    }

    @Override
    public Long increaseUnSeqId(int code) {
        return idGenerateService.getUnSeqId(code);
    }
}
