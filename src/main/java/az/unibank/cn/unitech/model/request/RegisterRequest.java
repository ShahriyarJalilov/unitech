package az.unibank.cn.unitech.model.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterRequest {
    @NonNull @NotBlank
    String pin;
    @NonNull @NotBlank
    String password;
}
