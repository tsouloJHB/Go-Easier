package za.co.wethinkcode.server.database.datainterfaceobject;


import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;

public interface GpsTravelDoi extends BaseQuery{
    
    @Update("INSERT INTO gpsTravel(userId,entryGpsLocation,exitGpsLocation ,date ,token)"
    +"VALUES (?{1},?{2},?{3},?{4},?{5})")
    public void createTransactions(int userId,Double entryGpsLocation,Double exitGpsLocation,String date,String token);

    @Update("CREATE TABLE IF NOT EXISTS gpsTravel("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" userId INTEGER NOT NULL, "
    +" entryGpsLocation INT NOT NULL, "
    +" exitGpsLocation INT NOT NULL, "
    +" date TEXT NOT NULL, "
    +" token TEXT INT NOT NULL)")
    public void createGpsTravelTable();
}
