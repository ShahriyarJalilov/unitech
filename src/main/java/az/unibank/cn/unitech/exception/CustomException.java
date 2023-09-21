package az.unibank.cn.unitech.exception;


import az.unibank.cn.unitech.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
    }

}
