package az.unibank.cn.unitech.model.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TokenDto implements Serializable {
    private String accessToken;
    private String refreshToken;
}
