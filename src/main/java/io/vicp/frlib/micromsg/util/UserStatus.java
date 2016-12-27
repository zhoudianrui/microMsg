package io.vicp.frlib.micromsg.util;

/**
 * Created by zhoudr on 2016/12/27.
 */
public enum UserStatus {
    USER_NOT_REGISTER_STATUS(0, "未注册"),
    USER_NOT_LOGIN_STATUS(1, "未登录"),
    USER_NOT_ACTIVATED_STATUS(2, "未激活"),
    USER_NORMAL_STATUS(3, "正常");

    private int value;

    private String info;

    public int getValue () {
        return value;
    }

    public String getInfo() {
        return info;
    }

    private UserStatus(int value, String info) {
        this.value = value;
        this.info = info;
    }
}
