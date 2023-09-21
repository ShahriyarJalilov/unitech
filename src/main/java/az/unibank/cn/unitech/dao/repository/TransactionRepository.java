package az.unibank.cn.unitech.dao.repository;

import az.unibank.cn.unitech.dao.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
