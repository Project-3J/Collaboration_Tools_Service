package com.agile.demo.biz.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUserId(String userId);

    Optional<AccountEntity> deleteByUserId(String userId);

    boolean existsByUserId(String userId);

    List<AccountEntity> findAll();
}
