package org.live.api.vo;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 11:27
 */
public class UserLoginVO {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserLoginVO{" +
                "userId=" + userId +
                '}';
    }
}
