package az.unibank.cn.unitech.dao.repository;


import az.unibank.cn.unitech.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserPinAndActive(String pin, int active);
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}

