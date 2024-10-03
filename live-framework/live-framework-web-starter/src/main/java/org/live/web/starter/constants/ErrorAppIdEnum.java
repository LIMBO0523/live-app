package org.live.web.starter.constants;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:47
 */
public enum ErrorAppIdEnum {
    API_ERROR(101,"live-api");

    ErrorAppIdEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
