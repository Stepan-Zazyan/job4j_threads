package ru.job4j.cash;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return Objects.equals(account, accounts.putIfAbsent(account.id(), account));
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return Objects.equals(accounts.get(id), accounts.remove(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean res = false;
        Optional<Account> accountFrom = getById(fromId);
        Optional<Account> accountTo = getById(toId);
        if (accountFrom.isPresent() && accountTo.isPresent()) {
            int sumFrom = accounts.get(fromId).amount() - amount;
            if (sumFrom < 0) {
                throw new IllegalArgumentException("Few money on account");
            }
            int sumTo = accounts.get(toId).amount() + amount;
            update(new Account(fromId, sumFrom));
            update(new Account(toId, sumTo));
            res = true;
        }
        return res;
    }
}
