package org.live.id.generate.enums;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:42
 */
public enum IdTypeEnum {
    USER_ID(1,"用户id生成策略");

    int code;
    String desc;

    IdTypeEnum(int code, String desc) {
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
