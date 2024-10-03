package org.live.msg.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 9:27
 */
public class MsgCheckDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4883763507970727755L;
    private boolean checkStatus;
    private String desc;


    public MsgCheckDTO(boolean checkStatus, String desc) {
        this.checkStatus = checkStatus;
        this.desc = desc;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MsgCheckDTO{" +
                "checkStatus=" + checkStatus +
                ", desc='" + desc + '\'' +
                '}';
    }
}

