package az.unibank.cn.unitech.auth;

import az.unibank.cn.unitech.constant.ErrorMessage;
import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.response.TokenDto;
import az.unibank.cn.unitech.service.AuthProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import az.unibank.cn.unitech.model.request.AuthRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.service.AuthService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthProvider authProvider;

    private AuthRequest authRequest;

    private String pin;
    private String password;

    @BeforeEach
    void setup() {
        pin = "jltxhm";
        password = "12345";
        authRequest = new AuthRequest();
        authRequest.setPin(pin);
        authRequest.setPassword(password);
    }


    @Test
    public void testGenerateToken() {

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(pin, password)
        )).thenReturn(null);

        String expectedToken = "generatedToken";
        when(authProvider.generateTokenDto(pin)).thenReturn(TokenDto.builder().accessToken(expectedToken).build());

        CommonResponse<TokenDto> response = authService.generateToken(authRequest);

        assertEquals(HttpStatus.CREATED.value(), response.getCode());
    }

    @Test
    public void testGenerateTokenAuthenticationException() {
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(pin, password)
        )).thenThrow(new CustomException(ErrorMessage.ERROR_PIN_OR_PASSWORD_IS_NOT_CORRECT));

        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.generateToken(authRequest);
        });

        assertEquals("Pin ve ya sifre yalnishdir!", exception.getMessage());
    }
}


