package az.unibank.cn.unitech.controller;

import az.unibank.cn.unitech.model.request.AuthRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.model.response.TokenDto;
import az.unibank.cn.unitech.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<CommonResponse<TokenDto>> generateToken(@RequestBody AuthRequest authRequest){
       return ResponseEntity.ok(authService.generateToken(authRequest));
    }
}
