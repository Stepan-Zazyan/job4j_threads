package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean res = false;
        if (!accounts.containsValue(account)) {
            accounts.put(account.id(), account);
            res = true;
        }
        return res;
    }

    public synchronized boolean update(Account account) {
        boolean res = false;
        accounts.put(account.id(), account);
        if (accounts.get(account.id()).equals(account)) {
            res = true;
        }
        return false;
    }

    public synchronized boolean delete(int id) {
        accounts.remove(id);
        return !accounts.containsKey(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int sumFrom = accounts.get(fromId).amount() - amount;
        if (sumFrom < 0) {
            throw new IllegalArgumentException("Few money on account");
        }
        Account accountFrom = new Account(fromId, sumFrom);
        int sumTo = accounts.get(toId).amount() + amount;
        Account accountTo = new Account(toId, sumTo);
        update(accountFrom);
        update(accountTo);
        return true;
    }
}
