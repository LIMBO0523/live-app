package org.live.user.interfaces;

import org.live.user.dto.UserDTO;

public interface IUserRpc {

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    UserDTO getByUserId(Long userId);

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    boolean updateUserInfo(UserDTO userDTO);

    /**
     * 插入用户
     * @param userDTO
     * @return
     */
    boolean insertOne(UserDTO userDTO);
}
