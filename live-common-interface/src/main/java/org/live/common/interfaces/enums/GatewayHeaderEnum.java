package org.live.common.interfaces.enums;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 13:06
 */
public enum GatewayHeaderEnum {

    USER_LOGIN_ID("用户id","gh_user_id");

    String desc;
    String name;

    GatewayHeaderEnum(String desc, String name) {
        this.desc = desc;
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }
}
