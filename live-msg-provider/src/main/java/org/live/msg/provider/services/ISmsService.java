package org.live.msg.provider.services;

import org.live.msg.dto.MsgCheckDTO;
import org.live.msg.emus.MsgSendResultEnum;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 9:17
 */
public interface ISmsService {

    /**
     * 发送短信接口
     *
     * @param phone
     * @return
     */
    MsgSendResultEnum sendMessage(String phone);

    /**
     * 校验登录验证码
     *
     * @param phone
     * @param code
     * @return
     */
    MsgCheckDTO checkLoginCode(String phone, Integer code);

    /**
     * 插入一条短信记录
     *
     * @param phone
     * @param code
     */
    void insertOne(String phone, Integer code);
}

