package az.unibank.cn.unitech.service;

import az.unibank.cn.unitech.dao.entity.UserEntity;
import az.unibank.cn.unitech.dao.repository.UserRepository;
import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.request.RegisterRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static az.unibank.cn.unitech.constant.ErrorMessage.ERROR_PIN_IS_ALREADY_EXIST;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public CommonResponse<UserEntity> registerUser(RegisterRequest request) {
        if (userRepository.findByPin(request.getPin()).isPresent()) {
            throw new CustomException(ERROR_PIN_IS_ALREADY_EXIST);
        }
        UserEntity user = UserEntity.builder()
                .pin(request.getPin())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();
        return CommonResponse.getInstance(HttpStatus.CREATED, userRepository.save(user));
    }
}
