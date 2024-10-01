package org.live.id.generate.interfaces;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 21:48
 */
public interface IdBuilderRpc {

    /**
     * 根据本地步长度来生成唯一id(区间性递增)
     *
     * @return
     */
    Long increaseSeqId(int code);

    /**
     * 生成的是非连续性id
     *
     * @param code
     * @return
     */
    Long increaseUnSeqId(int code);
}

