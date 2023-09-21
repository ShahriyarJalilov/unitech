package az.unibank.cn.unitech.exception;


import az.unibank.cn.unitech.constant.ErrorMessage;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class ErrorResponse implements Serializable {

    private final int code;
    private final String message;

    public ErrorResponse(ErrorMessage errorMessage) {
        this.code = errorMessage.getErrorCode();
        this.message = errorMessage.getErrorMessage();
    }

    public ErrorResponse(int errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public static ErrorResponse of(ErrorMessage ex) {
        return new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
    }

    public static ErrorResponse of(int errorCode,String errorMessage) {
        return new ErrorResponse(errorCode, errorMessage);
    }
}

