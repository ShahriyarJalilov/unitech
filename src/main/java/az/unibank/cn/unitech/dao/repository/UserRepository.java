package az.unibank.cn.unitech.dao.repository;


import az.unibank.cn.unitech.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPin(String pin);
}

