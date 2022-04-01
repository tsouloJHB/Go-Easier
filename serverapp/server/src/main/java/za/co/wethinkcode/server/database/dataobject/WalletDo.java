package za.co.wethinkcode.server.database.dataobject;

public class WalletDo {
    int id;
    int userId;
    Double balance;

    public int getUserId(){
        return userId;
    }

    public Double getBalance(){
        return balance;
    }

}
