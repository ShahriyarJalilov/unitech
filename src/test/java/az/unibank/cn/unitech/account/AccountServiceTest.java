package az.unibank.cn.unitech.account;

import az.unibank.cn.unitech.dao.entity.AccountEntity;
import az.unibank.cn.unitech.dao.entity.TransactionEntity;
import az.unibank.cn.unitech.dao.entity.UserEntity;
import az.unibank.cn.unitech.dao.repository.AccountRepository;
import az.unibank.cn.unitech.dao.repository.TransactionRepository;
import az.unibank.cn.unitech.dao.repository.UserRepository;
import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.response.CommonResponse;
import az.unibank.cn.unitech.service.AccountService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private AccountEntity senderAccount;

    private AccountEntity receiverAccount;

    private UserEntity user;


    @BeforeEach
    void setup(){
        senderAccount = new AccountEntity();
        senderAccount.setId(1L);
        senderAccount.setAccountNumber("1234567");
        senderAccount.setBalance(new BigDecimal("1000"));
        senderAccount.setActive(1);

        receiverAccount = new AccountEntity();
        receiverAccount.setId(2L);
        receiverAccount.setAccountNumber("5432135");
        receiverAccount.setBalance(new BigDecimal("500"));
        receiverAccount.setActive(1);

        user = new UserEntity();
    }

    @Test
    public void testGetActiveAccounts() {
        String pin = "123456";
        user.setPin(pin);

        List<AccountEntity> accountList = new ArrayList<>();

        senderAccount.setActive(1);
        accountList.add(senderAccount);
        receiverAccount.setActive(0);
        accountList.add(receiverAccount);

        when(userRepository.findByPin(pin)).thenReturn(Optional.of(user));
        when(accountRepository.findByUserPinAndActive(pin, 1)).thenReturn(accountList);

        CommonResponse<List<AccountEntity>> response = accountService.getActiveAccounts(pin);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(accountList, response.getData());
    }

    @Test
    public void testSuccessfulTransfer() {
        when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber())).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber())).thenReturn(Optional.of(receiverAccount));

        accountService.transferMoney(senderAccount.getAccountNumber(), receiverAccount.getAccountNumber(), new BigDecimal("200"));

        assertEquals(new BigDecimal("800"), senderAccount.getBalance());
        assertEquals(new BigDecimal("700"), receiverAccount.getBalance());

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    public void testInsufficientBalance() {
        senderAccount.setBalance(new BigDecimal(100));
        receiverAccount.setBalance(new BigDecimal(500));

        when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber())).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber())).thenReturn(Optional.of(receiverAccount));

        assertThrows(CustomException.class, () -> {
            accountService.transferMoney(
                    senderAccount.getAccountNumber(), receiverAccount.getAccountNumber(), new BigDecimal("200"));
        });

        assertEquals(new BigDecimal("100"), senderAccount.getBalance());
        assertEquals(new BigDecimal("500"), receiverAccount.getBalance());

        verify(transactionRepository, never()).save(any(TransactionEntity.class));
    }
}

