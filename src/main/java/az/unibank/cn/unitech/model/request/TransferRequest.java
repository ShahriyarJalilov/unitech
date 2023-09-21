package az.unibank.cn.unitech.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRequest {
    @NotNull @NotBlank
    String senderAccountNumber;
    @NotNull @NotBlank
    String receiverAccountNumber;
    @NotNull
    BigDecimal amount;

}

