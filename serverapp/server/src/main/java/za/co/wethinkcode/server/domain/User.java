package za.co.wethinkcode.server.domain;

public class User {
    int userId;
    String username;
    String email;
    int walletId;

    public User(int userId,String username,String email,int walletId){
        this.userId = userId;
        this.username = username;
        this.email  = email;
        this.walletId = walletId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getWalletId() {
        return walletId;
    }


    //setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }
}
