package az.unibank.cn.unitech.controller;

import az.unibank.cn.unitech.dao.entity.AccountEntity;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/get-active")
    public ResponseEntity<CommonResponse<List<AccountEntity>>> getActiveAccounts(
            @NotNull @NotBlank @Size(min = 6) @RequestParam String pin
    ) {
        return ResponseEntity.ok(accountService.getActiveAccounts(pin));
    }

}


