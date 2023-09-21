package az.unibank.cn.unitech.service;

import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.model.response.CurrencyRateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static az.unibank.cn.unitech.constant.ErrorMessage.ERROR_DATA_NOT_FOUND;

@Service
public class CurrencyRateService {
     Map<String, Double> currencyRates = new HashMap<>();

    public CommonResponse<CurrencyRateResponse> rateResponse(String key) {
        if (!currencyRates.containsKey(key)) {
            throw new CustomException(ERROR_DATA_NOT_FOUND);
        }
        return CommonResponse.getInstance(
                HttpStatus.OK,
                new CurrencyRateResponse().setRateAmount(currencyRates.get(key)));
    }

    //mock data
    @Scheduled(fixedRate = 60000)
    public void updateCurrencyRates() {
        currencyRates.put("USD/AZN", getRandomRate());
        currencyRates.put("AZN/USD", getRandomRate());
        currencyRates.put("AZN/TL", getRandomRate());
        currencyRates.put("TL/AZN", getRandomRate());
    }

    private Double getRandomRate() {
        return 1.0 + Math.random() * 10.0;
    }
}

