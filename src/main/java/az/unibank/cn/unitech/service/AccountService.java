package az.unibank.cn.unitech.service;

import az.unibank.cn.unitech.dao.entity.AccountEntity;
import az.unibank.cn.unitech.dao.entity.TransactionEntity;
import az.unibank.cn.unitech.dao.entity.UserEntity;
import az.unibank.cn.unitech.dao.repository.AccountRepository;
import az.unibank.cn.unitech.dao.repository.TransactionRepository;
import az.unibank.cn.unitech.dao.repository.UserRepository;
import az.unibank.cn.unitech.exception.CustomException;
import az.unibank.cn.unitech.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static az.unibank.cn.unitech.constant.ErrorMessage.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    public CommonResponse<List<AccountEntity>> getActiveAccounts(String pin) {
        UserEntity user = userRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(ERROR_PIN_NOT_EXIST));
        return CommonResponse.getInstance(HttpStatus.OK,
                accountRepository.findByUserPinAndActive(user.getPin(), 1)
        );
    }


    @Transactional
    public void transferMoney(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {

        AccountEntity senderAccount = accountRepository.findByAccountNumber(senderAccountNumber)
                .orElseThrow(() -> new CustomException(ERROR_SENDER_ACCOUNT_NOT_FOUND));

        AccountEntity receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new CustomException(ERROR_RECEIVER_ACCOUNT_NOT_FOUND));

        if (senderAccount.getId().equals(receiverAccount.getId())) {
            throw new CustomException(ERROR_SENDER_AND_RECEIVER_ACCOUNTS_CANNOT_BE_SAME);
        }

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new CustomException(ERROR_INSUFFICIENT_BALANCE_IN_THE_SENDER_ACCOUNT);
        }

        if (receiverAccount.getActive() != 1) {
            throw new CustomException(ERROR_RECEIVER_ACCOUNT_IS_NOT_ACTIVE);
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

        TransactionEntity transaction = TransactionEntity.builder()
                .senderAccountNumber(senderAccountNumber)
                .receiverAccountNumber(receiverAccountNumber)
                .amount(amount)
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
    }
}

