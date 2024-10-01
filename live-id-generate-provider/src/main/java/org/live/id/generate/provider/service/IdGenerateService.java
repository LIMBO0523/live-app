package org.live.id.generate.provider.service;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 21:50
 */
public interface IdGenerateService {

    /**
     * 生成有序id
     *
     * @param code
     * @return
     */
    Long getSeqId(Integer code);

    /**
     * 生成无序id
     *
     * @param code
     * @return
     */
    Long getUnSeqId(Integer code);
}

