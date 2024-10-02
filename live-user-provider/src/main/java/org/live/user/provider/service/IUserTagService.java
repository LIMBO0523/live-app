package org.live.user.provider.service;

import org.live.user.constants.UserTagsEnum;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 23:17
 */
public interface IUserTagService {

    /**
     * 设置标签 只能设置成功一次
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    boolean setTag(Long userId, UserTagsEnum userTagsEnum);

    /**
     * 取消标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    boolean cancelTag(Long userId,UserTagsEnum userTagsEnum);

    /**
     * 是否包含某个标签
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    boolean containTag(Long userId, UserTagsEnum userTagsEnum);
}
