package org.live.api.services.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.live.api.services.IHomePageService;
import org.live.api.vo.HomePageVO;
import org.live.user.constants.UserTagsEnum;
import org.live.user.dto.UserDTO;
import org.live.user.interfaces.IUserRpc;
import org.live.user.interfaces.IUserTagRpc;
import org.springframework.stereotype.Service;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:17
 */
@Service
public class HomePageServiceImpl implements IHomePageService {

    @DubboReference
    private IUserRpc userRpc;
    @DubboReference
    private IUserTagRpc userTagRpc;

    @Override
    public HomePageVO initPage(Long userId) {
        UserDTO userDTO = userRpc.getByUserId(userId);
        HomePageVO homePageVO = new HomePageVO();
        if (userDTO != null) {
            homePageVO.setAvatar(userDTO.getAvatar());
            homePageVO.setUserId(userId);
            homePageVO.setNickName(userDTO.getNickName());
            //vip用户有权利开播
            homePageVO.setShowStartLivingBtn(userTagRpc.containTag(userId, UserTagsEnum.IS_VIP));
        }
        return homePageVO;
    }
}
