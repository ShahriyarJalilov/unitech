package az.unibank.cn.unitech.service;

import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.request.AuthRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.model.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import static az.unibank.cn.unitech.constant.ErrorMessage.ERROR_PIN_OR_PASSWORD_IS_NOT_CORRECT;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthProvider authProvider;


    public CommonResponse<TokenDto> generateToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getPin(), authRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new CustomException(ERROR_PIN_OR_PASSWORD_IS_NOT_CORRECT);
        }
        return CommonResponse.getInstance(HttpStatus.CREATED, authProvider.generateTokenDto(authRequest.getPin()));
    }
}
