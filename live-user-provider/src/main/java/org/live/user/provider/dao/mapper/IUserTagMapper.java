package org.live.user.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.live.user.provider.dao.po.UserTagPO;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 23:18
 */
@Mapper
public interface IUserTagMapper extends BaseMapper<UserTagPO> {

    @Update("update t_user_tag set ${fieldName}=${fieldName} | #{tag} where user_id=#{userId} and ${fieldName} & #{tag} = 0")
    int setTag(Long userId, String fieldName, long tag);

    @Update("update t_user_tag set ${fieldName}=${fieldName} &~ #{tag} where user_id=#{userId} and ${fieldName} & #{tag} == 0")
    int cancelTag(Long userId, String fieldName, long tag);
}

