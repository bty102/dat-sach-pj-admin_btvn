package com.bty.datsachpj.datsachpjadmin_btvn.config;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Account;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataInit implements CommandLineRunner {

    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;
    String ADMIN_USERNAME = "admin";
    String ADMIN_PASSWORD = "admin";

    @Override
    public void run(String... args) throws Exception {
        if(!accountRepository.existsByUsername(ADMIN_USERNAME)) {
            Account admin = Account.builder()
                    .username(ADMIN_USERNAME)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .authority(true)
                    .build();
            accountRepository.save(admin);
        }
        if(!accountRepository.existsByUsername("bty")) {
            Account bty = Account.builder()
                    .username("bty")
                    .password(passwordEncoder.encode("bty"))
                    .authority(false)
                    .build();
            accountRepository.save(bty);
        }
    }
}
