package az.unibank.cn.unitech.service;

import az.unibank.cn.unitech.dao.entity.UserEntity;
import az.unibank.cn.unitech.dao.repository.UserRepository;
import az.unibank.cn.unitech.exception.CustomException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static az.unibank.cn.unitech.constant.ErrorMessage.ERROR_PIN_NOT_EXIST;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String pin) {
        UserEntity user = userRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(ERROR_PIN_NOT_EXIST));
        return new User(
                user.getPin(),
                user.getPassword(),
                Collections.emptyList());
    }
}
