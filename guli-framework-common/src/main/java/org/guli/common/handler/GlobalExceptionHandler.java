package org.guli.common.handler;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.guli.common.exception.GuliException;
import org.guli.common.util.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by leyi on 2019/6/4.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GuliException.class)
    public R error(GuliException e) {

        // e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.failed(e.getMessage()).setCode(e.getCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R error(HttpMessageNotReadableException e) {

        log.error(ExceptionUtils.getMessage(e));
        return R.failed("json解析异常！");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R error(IllegalArgumentException e){

        log.error(ExceptionUtils.getMessage(e));
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public R error(BadSqlGrammarException e) {

        log.error(ExceptionUtils.getMessage(e));
        return R.failed("sql语法错误！");
    }

    @ExceptionHandler(Exception.class)
    public R error(Exception e) {

        log.error(ExceptionUtils.getMessage(e));
        return R.failed(e.getMessage());
    }

}
