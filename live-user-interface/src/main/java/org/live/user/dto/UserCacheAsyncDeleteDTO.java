package org.live.user.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author LIMBO0523
 * @Date 2024/10/2 11:14
 */
public class UserCacheAsyncDeleteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 182902135334036100L;
    /**
     * 不同的业务场景的code，区别不同的延迟消息
     */
    private int code;
    private String json;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
