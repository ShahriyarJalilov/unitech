package az.unibank.cn.unitech.controller;

import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.model.response.CurrencyRateResponse;
import az.unibank.cn.unitech.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/currency")
@Validated
@RequiredArgsConstructor
public class CurrencyRateController {


    private final CurrencyRateService currencyRateService;


    @GetMapping("")
    public ResponseEntity<CommonResponse<CurrencyRateResponse>> getCurrencyRate(
            @NotNull @NotBlank @RequestParam String currencyPair
    ) {
        return ResponseEntity.ok(currencyRateService.rateResponse(currencyPair));
    }
}
