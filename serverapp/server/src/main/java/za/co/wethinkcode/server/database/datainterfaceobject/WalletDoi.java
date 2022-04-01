package za.co.wethinkcode.server.database.datainterfaceobject;

import java.util.List;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.server.database.dataobject.WalletDo;
import net.lemnik.eodsql.Select;

public interface WalletDoi extends BaseQuery {

    @Update("INSERT INTO wallet(balance,userId,date)"
    +"VALUES (?{1},?{2},?{3})")
    public void createWallet(int balance,int userId,String date);

    @Select("SELECT * FROM wallet WHERE id = ?{1} ")
    WalletDo getWalletById(int id);

    @Select("SELECT * FROM wallet WHERE userId = ?{1} ")
    WalletDo getWalletByUserId(int userId);

    @Update("UPDATE  wallet SET balance = ?{1} WHERE id = ?{2}")
    public void updateWallet(Double balance,int userId);



    @Update("CREATE TABLE IF NOT EXISTS wallet("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" balance INTEGER NOT NULL, "
    +" date TEXT NOT NULL, "
    +" userId  INT NOT NULL)")
    public void createWalletTable();    
}
