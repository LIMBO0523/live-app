package org.live.api.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.live.user.dto.UserDTO;
import org.live.user.interfaces.IUserRpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TestController {

    @DubboReference
    private IUserRpc userRpc;

    @GetMapping("/getUserInfo")
    public UserDTO getUserInfo(Long userId) {
        return userRpc.getByUserId(userId);
    }

    @GetMapping("/updateUserInfo")
    public boolean getUserInfo(Long userId, String nickName) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(nickName);
        userDTO.setUserId(userId);
        return userRpc.updateUserInfo(userDTO);
    }

    @GetMapping("/insertOne")
    public boolean insertOne(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName("idea-test");
        userDTO.setSex(1);
        return userRpc.insertOne(userDTO);
    }

    @GetMapping("/batchQueryUserInfo")
    public Map<Long,UserDTO> batchQueryUserInfo(String userIdStr) {
        String[] idStr = userIdStr.split(",");
        List<Long> userIdList = new ArrayList<>();
        for (String userId : idStr) {
            userIdList.add(Long.valueOf(userId));
        }
        return userRpc.batchQueryUserInfo(userIdList);
    }

}
