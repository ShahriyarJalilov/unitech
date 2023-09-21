package az.unibank.cn.unitech.currencyrate;

import az.unibank.cn.unitech.constant.ErrorMessage;
import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.model.response.CurrencyRateResponse;
import az.unibank.cn.unitech.service.CurrencyRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateServiceTest {

    @Mock
    private CurrencyRateService currencyRateService;


    @Test
    public void testRateResponse() {
        String currencyPair = "USD/AZN";

        when(currencyRateService.rateResponse(currencyPair)).thenReturn(CommonResponse.getInstance(
                HttpStatus.OK,
                new CurrencyRateResponse().setRateAmount(1.7)));

        CommonResponse<CurrencyRateResponse> response = currencyRateService.rateResponse(currencyPair);
        Double usdToAznRate = response.getData().getRateAmount();

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(usdToAznRate);
        assertTrue(usdToAznRate >= 1.0 && usdToAznRate <= 11.0);
    }

    @Test
    public void testRateResponseNotFound() {
        String currencyPair = "EUR/GBP";

        when(currencyRateService.rateResponse(currencyPair))
                .thenThrow(new CustomException(ErrorMessage.ERROR_DATA_NOT_FOUND));

        CustomException exception = assertThrows(CustomException.class, () -> {
            currencyRateService.rateResponse(currencyPair);
        });

        assertEquals("Melumat tapilmadi", exception.getMessage());
        assertEquals(-9, exception.getErrorMessage().getErrorCode());
    }


}



