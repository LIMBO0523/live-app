package org.live.common.interfaces.enums;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:40
 */
public enum CommonStatusEum {
    INVALID_STATUS(0,"无效"),
    VALID_STATUS(1,"有效");

    CommonStatusEum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    int code;
    String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
