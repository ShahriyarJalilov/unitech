package az.unibank.cn.unitech.constant;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    ERROR_ACCESS_TOKEN_EXPIRED(-10,"Etibarsiz token"),
    ERROR_DATA_NOT_FOUND(-9,"Melumat tapilmadi"),
    ERROR_RECEIVER_ACCOUNT_IS_NOT_ACTIVE(-8, "Qebul eden hesab aktiv hesab deyil"),
    ERROR_INSUFFICIENT_BALANCE_IN_THE_SENDER_ACCOUNT(-7, "Gonderen hesabinda kifayət qədər balans yoxdur"),
    ERROR_SENDER_AND_RECEIVER_ACCOUNTS_CANNOT_BE_SAME(-6, "Gonderen və qebul edən hesablar eyni ola bilməz"),
    ERROR_RECEIVER_ACCOUNT_NOT_FOUND(-5, "Qebul eden hesab tapilmadi"),
    ERROR_SENDER_ACCOUNT_NOT_FOUND(-4, "Gonderen hesab tapilmadi"),
    ERROR_PIN_IS_ALREADY_EXIST(-3, "Pin artiq movcuddur"),
    ERROR_PIN_OR_PASSWORD_IS_NOT_CORRECT(-2, "Pin ve ya sifre yalnishdir!"),
    ERROR_PIN_NOT_EXIST(-1, "Pin movcud deyil");

    private final int errorCode;
    private final String errorMessage;

    ErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
