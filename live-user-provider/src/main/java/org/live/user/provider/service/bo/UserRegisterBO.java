package org.live.user.provider.service.bo;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:34
 */
public class UserRegisterBO {
    private Long userId;
    private String phone;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
