package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Account;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetailsService implements UserDetailsService {

    final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsernameAndAuthority(username, true)
                .orElseThrow(() -> {throw new UsernameNotFoundException("User not found with username:" + username);});

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .build();
    }
}
