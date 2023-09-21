package az.unibank.cn.unitech.register;

import az.unibank.cn.unitech.dao.entity.TransactionEntity;
import az.unibank.cn.unitech.dao.entity.UserEntity;
import az.unibank.cn.unitech.dao.repository.UserRepository;
import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.request.RegisterRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RegisterRequest registerRequest;
    private UserEntity user;
    private  String pin;


    @BeforeEach
    void setup() {
        pin = "jltxhm";
        String password = "12345";
        registerRequest = new RegisterRequest();
        registerRequest.setPin(pin);
        registerRequest.setPassword(password);

        user = new UserEntity();
        user.setId(1L);
        user.setPin(pin);
        user.setPassword(password);
    }


    @Test
    public void testRegisterUser() {
        when(registrationService.registerUser(registerRequest))
                .thenReturn(CommonResponse.getInstance(HttpStatus.CREATED, user));

        CommonResponse<UserEntity> response = registrationService.registerUser(registerRequest);

        assertEquals(HttpStatus.CREATED.value(), response.getCode());
        verify(userRepository, timeout(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterIsAlreadyExits() {
        when(userRepository.findByPin(pin)).thenReturn(Optional.of(new UserEntity()));

        CustomException exception = assertThrows(CustomException.class, () -> {
            registrationService.registerUser(registerRequest);
        });

        assertEquals("Pin artiq movcuddur", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}

