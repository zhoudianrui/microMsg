package io.vicp.frlib.micromsg.web.model;

import lombok.Data;

/**
 * 用户基本信息
 * Created by zhoudr on 2016/12/27.
 */
@Data
public class User {
    // 主键
    private long id;
    // 姓名
    private String name;
}
