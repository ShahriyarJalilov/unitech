package az.unibank.cn.unitech.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationProperty {
    @Value("${jwt.issuer}")
    private String jwtIssuer;
    @Value("${jwt.alias}")
    private String jwtAlias;
    @Value("${jwt.expirationTime.token.accessInMinutes}")
    private Integer expirationAccessTokenInMinutes;
    @Value("${jwt.expirationTime.token.refreshInMonths}")
    private Integer expirationRefreshTokenInMonths;
}
