package az.unibank.cn.unitech.service;

import az.unibank.cn.unitech.config.ApplicationProperty;
import az.unibank.cn.unitech.model.response.TokenDto;
import az.unibank.cn.unitech.model.security.CustomClaimSet;
import az.unibank.cn.unitech.util.DateUtil;
import az.unibank.cn.unitech.util.JwtUtil;
import az.unibank.cn.unitech.util.MapperUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthProvider {

    private final KeyService keyService;
    private final ApplicationProperty applicationProperty;

    public TokenDto generateTokenDto(String pin) {
        return TokenDto.builder()
                .accessToken(prepareJWT(pin))
                .refreshToken(prepareRefreshJWT(pin))
                .build();
    }

    private String prepareRefreshJWT(String pin) {
        return JwtUtil
                .of(
                        MapperUtil.mapToString(
                                prepareClaimSet(pin).setExpirationTime(
                                        DateUtil
                                                .now()
                                                .plusMonths(applicationProperty.getExpirationRefreshTokenInMonths())
                                                .toDate()
                                )
                        )
                )
                .withKey(keyService.getPrivateKey())
                .serialize();
    }

    private String prepareJWT(String pin) {
        return JwtUtil
                .of(MapperUtil.mapToString(prepareClaimSet(pin)))
                .withKey(keyService.getPrivateKey())
                .serialize();
    }

    private CustomClaimSet prepareClaimSet(String pin) {
        return CustomClaimSet.builder()
                .pin(pin)
                .issuer(applicationProperty.getJwtIssuer())
                .expirationTime(
                        DateUtil
                                .now()
                                .plusMinutes(applicationProperty.getExpirationAccessTokenInMinutes())
                                .toDate()
                )
                .build();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        var jwtClaimsSet = parseAndVerifyToken(token);
        final String username = jwtClaimsSet.getSubject();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtClaimsSet));
    }

    private boolean isTokenExpired(JWTClaimsSet claimSet) {
        return claimSet.getExpirationTime().before(new Date());
    }

    public JWTClaimsSet parseAndVerifyToken(String token) {
        return JwtUtil
                .parse(token)
                .verify(keyService.getPublicKey());
    }
}
