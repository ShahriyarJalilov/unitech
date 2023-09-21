package az.unibank.cn.unitech.model.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomClaimSet {
    @JsonProperty("sub")
    private String pin;
    @JsonProperty("iss")
    private String issuer;
    @JsonProperty("exp")
    private Date expirationTime;
}

