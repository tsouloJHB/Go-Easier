package za.co.wethinkcode.server.database.dataobject;

public class UserDo {
    int id;
    String username;
    String email;
    int walletId;


    public int getId(){
        return this.id;
    }
    public int getWalletById(){
        return this.walletId;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }
}
