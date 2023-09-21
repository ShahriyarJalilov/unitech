package az.unibank.cn.unitech.controller;

import az.unibank.cn.unitech.dao.entity.UserEntity;
import az.unibank.cn.unitech.model.request.RegisterRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public ResponseEntity<CommonResponse<UserEntity>> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registrationService.registerUser(request));
    }
}
