package org.live.api.services;

import org.live.api.vo.HomePageVO;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:16
 */
public interface IHomePageService {


    /**
     * 初始化页面获取的信息
     *
     * @param userId
     * @return
     */
    HomePageVO initPage(Long userId);

}
