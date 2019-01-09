package com.funcas.pboot.conf;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.enumeration.ApiResultEnum;
import com.funcas.pboot.common.exception.ServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月14日
 */
@RestController
@ControllerAdvice
public class SystemExceptionController {

    @ExceptionHandler(ServiceException.class)
    public ApiResult serviceException(ServiceException exception) {
        return ApiResult.builder().apiResultEnum(ApiResultEnum.SERVICE_EXCEPTION).result(exception.getMessage()).build();
    }

    @ExceptionHandler
    public ApiResult globalException(Throwable throwable) {
        throwable.printStackTrace();
        return ApiResult.builder().apiResultEnum(ApiResultEnum.UNKNOWN_EXCEPTION).result(throwable.getMessage()).build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResult bindException(MethodArgumentNotValidException exception) {
        return ApiResult.builder().apiResultEnum(ApiResultEnum.VALIDATION_FAILURE)
                .result(exception.getBindingResult().getAllErrors()).build();
    }
}
