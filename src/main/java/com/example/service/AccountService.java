package com.example.service;

import com.example.repository.AccountRepository;

import org.springframework.stereotype.Service;

import com.example.entity.Account;

@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Account authenticate(String username, String password) {

        Account existingAccount = repo.findByUsername(username);

        if (existingAccount != null && existingAccount.getPassword().equals(password)) {
            return existingAccount;
        }

        return null;
    }

    public Account findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Account registerAccount(Account account) {
        return repo.save(account);
    }

    public boolean usernameExists(String username) {
        return repo.existsByUsername(username);
    }

}
