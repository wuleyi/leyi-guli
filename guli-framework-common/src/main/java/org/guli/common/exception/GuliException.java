package org.guli.common.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by leyi on 2019/6/4.
 */
@Data
@ApiModel(value = "全局异常")
public class GuliException extends RuntimeException {


    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 消息
     *
     * @param msg
     */
    public GuliException(String msg) {
        super(msg);
        this.code = 1110;
    }

    /**
     * 接受状态码和消息
     *
     * @param code
     * @param msg
     */
    public GuliException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    @Override
    public String toString() {
        return "GuliException{" + "message=" + this.getMessage() + "，code=" + code + "}";
    }

}
