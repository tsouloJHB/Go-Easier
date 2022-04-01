package za.co.wethinkcode.server.domain.wallet;

import java.sql.Date;
import java.time.LocalDate;

import za.co.wethinkcode.server.domain.User;

public interface Bank {

    Wallet getWallet(User user);
    Wallet createWallet(User user);
    // Wallet updateWallet(User user);
    void deposit(User user,double amount,String date,String token);
    void withdraw(User user,double amount,String date,String token);
}
