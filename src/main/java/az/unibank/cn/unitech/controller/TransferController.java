package az.unibank.cn.unitech.controller;

import az.unibank.cn.unitech.model.request.TransferRequest;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final AccountService accountService;


    @PostMapping
    public ResponseEntity<CommonResponse<String>> transferMoney(@Valid @RequestBody TransferRequest transferRequest) {
        accountService.transferMoney(
                transferRequest.getSenderAccountNumber(),
                transferRequest.getReceiverAccountNumber(),
                transferRequest.getAmount()
        );
        return ResponseEntity.ok(CommonResponse.successInstance());

    }

}

