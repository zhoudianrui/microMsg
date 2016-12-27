package io.vicp.frlib.micromsg.web.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户扩展信息
 * Created by zhoudr on 2016/12/27.
 */
@Data
public class UserInfo{
    private Date loginTime;
    private User user;
}
