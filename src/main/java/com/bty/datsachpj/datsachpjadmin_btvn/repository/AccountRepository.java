package com.bty.datsachpj.datsachpjadmin_btvn.repository;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsernameAndAuthority(String username, boolean authority);

    boolean findByUsername(String username);

    boolean existsByUsername(String username);
}
