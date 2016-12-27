package io.vicp.frlib.micromsg.exception;

import lombok.Data;

/**
 * Created by zhoudr on 2016/12/27.
 */
@Data
public class BusinessException extends RuntimeException {
    private int status;

    private String msg;

    public BusinessException(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
