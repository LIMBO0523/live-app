package org.live.user.provider.service;

import org.live.user.dto.UserDTO;
import org.live.user.provider.dao.po.UserPO;

public interface IUserService {
    UserDTO getByUserId(Long userId);

    boolean updateUserInfo(UserDTO userDTO);

    boolean insert(UserDTO userDTO);
}
