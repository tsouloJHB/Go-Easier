package za.co.wethinkcode.server.database.datainterfaceobject;


import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import net.lemnik.eodsql.Select;
import za.co.wethinkcode.server.database.dataobject.BusDo;

public interface BusDoi extends BaseQuery{

    @Update("INSERT INTO bus(name,busNumber,route ,qrCode )"
    +"VALUES (?{1},?{2},?{3},?{4})")
    public void createBus(String name, String busNumber,String route,String qrCode );
    
    @Select("SELECT * FROM bus WHERE qrCode = ?{1}")
    BusDo getBusByQrCode(String qrCode);

    @Update("CREATE TABLE IF NOT EXISTS bus("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" name TEXT NOT NULL , "
    +" busNumber TEXT NOT NULL UNIQUE, "
    +" route TEXT NOT NULL, "
    +" qrCode TEXT NOT NULL UNIQUE)")
    public void createBusStationsTable();
}

