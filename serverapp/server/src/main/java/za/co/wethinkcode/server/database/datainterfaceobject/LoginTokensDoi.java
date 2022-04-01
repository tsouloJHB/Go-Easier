package za.co.wethinkcode.server.database.datainterfaceobject;


import java.util.List;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.server.database.dataobject.LoginTokensDo;
import za.co.wethinkcode.server.database.dataobject.TransactionsDo;
import net.lemnik.eodsql.Select;

public interface LoginTokensDoi extends BaseQuery{


    @Update("DELETE FROM loginTokens")
    public void dropTable();


    @Update("DELETE FROM loginTokens WHERE token = ?{1}")
    public void deleteTokenById(String token);

    @Update("INSERT INTO loginTokens(userId,active,date,token)"
    +"VALUES (?{1},?{2},?{3},?{4})")
    public void createToken(int userId,String active,String date,String token );

    @Select("SELECT * FROM loginTokens WHERE userId = ?{1} ")
    LoginTokensDo getTokentByUserId(int id);

    @Select("SELECT * FROM loginTokens WHERE token = ?{1} ")
    LoginTokensDo getLoginTokensByToken(String token);

    @Update("CREATE TABLE IF NOT EXISTS loginTokens("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" userId INTEGER NOT NULL, "
    +" active TEXT NOT NULL, "
    +" date TEXT NOT NULL, "
    +" token TEXT INT NOT NULL)")
    public void createLoginTokensTable();  
    
}
