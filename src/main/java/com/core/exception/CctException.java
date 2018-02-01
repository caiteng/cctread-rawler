package com.core.exception;

/**
 * 常春藤业务异常
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-02-01 10:22
 */
public class CctException extends RuntimeException {

    public CctException() {
        super();
    }

    public CctException(String message) {
        super(message);
    }

}
