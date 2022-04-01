package za.co.wethinkcode.server.database.datainterfaceobject;


import java.util.List;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.server.database.dataobject.TransactionsDo;
import net.lemnik.eodsql.Select;

public interface TransactionsDoi extends BaseQuery {


    @Update("INSERT INTO transactions(amount,description,walletId,date,userId)"
    +"VALUES (?{1},?{2},?{3},?{4},?{5})")
    public void createTransactions(Double amount,String description ,int walletId,String date,int userId);

    @Select("SELECT * FROM transactions WHERE userId = ?{1} ")
    List<TransactionsDo> getTransactionById(int id);

    @Select("SELECT * FROM transactions WHERE WalletId = ?{1} ")
    List<TransactionsDo> getTransactionByWalletId(int id);

    @Update("CREATE TABLE IF NOT EXISTS transactions("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" amount INTEGER NOT NULL, "
    +" description TEXT NOT NULL, "
    +" walletId INTEGER NOT NULL, "
    +" date TEXT NOT NULL, "
    +" userId TEXT INT NOT NULL)")
    public void createTransactionsTable();  
}
