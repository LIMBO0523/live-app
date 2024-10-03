package org.live.user.provider.service;

import org.live.user.dto.UserPhoneDTO;

import java.util.List;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:16
 */
public interface IUserPhoneService {

    /**
     * 手机号注册
     *
     * @param phone
     * @param userId
     * @return
     */
    UserPhoneDTO insert(String phone, Long userId);

    /**
     * 更具用户 id 查询手机信息
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
