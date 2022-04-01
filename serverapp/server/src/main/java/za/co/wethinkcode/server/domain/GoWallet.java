package za.co.wethinkcode.server.domain;


import za.co.wethinkcode.server.domain.wallet.Wallet;

public class GoWallet extends Wallet {
    int walletId;
    Double balance;

    public GoWallet(int walletId,Double balance){
        this.walletId = walletId;
        this.balance = balance;
    }

    public Double getBalance(){
        return balance;
    }

 

 

    

    

}
