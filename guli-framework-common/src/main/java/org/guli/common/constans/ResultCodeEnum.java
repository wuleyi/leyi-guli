package org.guli.common.constans;

import lombok.Getter;

/**
 * Created by leyi on 2019/6/4.
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(true, 0,"成功"),
    UNKNOWN_REASON(false, -1, "未知错误");

    private Boolean success;

    private Integer code;

    private String message;

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}