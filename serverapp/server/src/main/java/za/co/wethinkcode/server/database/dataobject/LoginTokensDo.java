package za.co.wethinkcode.server.database.dataobject;

public class LoginTokensDo {
    public int id;
    public int userId;
    public String active;
    public  String Date;
    public String token;


    public String getLoginToken(){
        return this.token;
    }
    public int getUserId(){
        return userId;
    }
}
