package za.co.wethinkcode.server.domain.wallet;

import java.sql.Date;
import java.time.LocalDate;

import za.co.wethinkcode.server.domain.GoWallet;
import za.co.wethinkcode.server.domain.User;
import za.co.wethinkcode.server.api.GoServer;
import za.co.wethinkcode.server.database.DataBaseHandler;

public abstract class Wallet implements Bank{
    public DataBaseHandler dataBaseHandler = GoServer.dataBaseHandler;

    private int walletId;

    @Override
    public Wallet getWallet(User user) {
        return null;
    }

    @Override
    public Wallet createWallet(User user) {
        return null;
    }

    // @Override
    // public Wallet updateWallet(User user) {
    //     return null;
    // }

    @Override
    public void deposit(User user,double amount,String date,String token) {
        //get user wallet
        dataBaseHandler = GoServer.dataBaseHandler;

        //get wallet amount
        GoWallet wallet = dataBaseHandler.getWallet(token);
        Double newAmount = wallet.getBalance() + amount;
        dataBaseHandler.updateWallet(user, newAmount,date.toString());


    }
    @Override
    public void withdraw(User user,double amount,String date,String token) {
           //get user wallet
        dataBaseHandler = GoServer.dataBaseHandler;

        //get wallet amount
        GoWallet wallet = dataBaseHandler.getWallet(token);
        Double newAmount = wallet.getBalance() - amount;
        dataBaseHandler.updateWallet(user, newAmount,date.toString());

    }

}
