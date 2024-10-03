package org.live.user.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.live.common.interfaces.enums.CommonStatusEum;
import org.live.common.interfaces.utils.ConvertBeanUtils;
import org.live.common.interfaces.utils.DESUtils;
import org.live.user.dto.UserPhoneDTO;
import org.live.user.provider.dao.mapper.IUserPhoneMapper;
import org.live.user.provider.dao.po.UserPhonePO;
import org.live.user.provider.service.IUserPhoneService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:16
 */
@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
    @Resource
    private IUserPhoneMapper userPhoneMapper;

    @Override
    public UserPhoneDTO insert(String phone, Long userId) {
        UserPhonePO userPhonePO = new UserPhonePO();
        userPhonePO.setUserId(userId);
        userPhonePO.setPhone(DESUtils.encrypt(phone));
        userPhonePO.setStatus(CommonStatusEum.VALID_STATUS.getCode());
        userPhoneMapper.insert(userPhonePO);
        return ConvertBeanUtils.convert(userPhonePO,
                UserPhoneDTO.class);
    }

    @Override
    public List<UserPhoneDTO> queryByUserId(Long userId) {
        //底层会走用户手机号的主键索引
        LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPhonePO::getUserId, userId);
        queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEum.VALID_STATUS.getCode());
        return ConvertBeanUtils.convertList(userPhoneMapper.selectList(queryWrapper), UserPhoneDTO.class);
    }

    @Override
    public UserPhoneDTO queryByPhone(String phone) {
        //底层会走用户 id 的辅助索引
        LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPhonePO::getPhone, DESUtils.encrypt(phone));
        queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEum.VALID_STATUS.getCode());
        queryWrapper.orderByDesc(UserPhonePO::getCreateTime);
        queryWrapper.last("limit 1");
        return ConvertBeanUtils.convert(userPhoneMapper.selectOne(queryWrapper),UserPhoneDTO.class);
    }
}
