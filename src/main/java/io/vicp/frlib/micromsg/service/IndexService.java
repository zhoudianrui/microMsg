package io.vicp.frlib.micromsg.service;

import lombok.Data;

/**
 * Created by zhoudr on 2016/12/24.
 */
@Data
public class IndexService {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

}
