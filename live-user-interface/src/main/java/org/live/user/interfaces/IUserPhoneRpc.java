package org.live.user.interfaces;

import org.live.user.dto.UserLoginDTO;
import org.live.user.dto.UserPhoneDTO;

import java.util.List;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:00
 */
public interface IUserPhoneRpc {
    /**
     * 手机号登录
     *
     * @param phone
     * @return
     */
    UserLoginDTO login(String phone);

    /**
     * 根据用户 id 查询手机信息
     *
     * @param userId
     * @return
     */
    List<UserPhoneDTO> queryByUserId(Long userId);

    /**
     * 根据手机号查询
     *
     * @param phone
     * @return
     */
    UserPhoneDTO queryByPhone(String phone);
}
