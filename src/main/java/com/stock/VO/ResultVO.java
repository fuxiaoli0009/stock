package com.stock.VO;

import lombok.Data;

@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    private String msg;

    private T data;

}
