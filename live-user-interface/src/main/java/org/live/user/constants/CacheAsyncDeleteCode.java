package org.live.user.constants;

/**
 * @Author LIMBO0523
 * @Date 2024/10/2 11:16
 */
public enum CacheAsyncDeleteCode {

    USER_INFO_DELETE(0, "用户信息删除"),
    USER_TAG_DELETE(1, "用户标签删除");

    final int code;
    final String desc;

    CacheAsyncDeleteCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
