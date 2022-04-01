package za.co.wethinkcode.server.database.datainterfaceobject;


import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import net.lemnik.eodsql.Select;
import za.co.wethinkcode.server.database.dataobject.BusStationsDo;

public interface BusStationsDoi extends BaseQuery{

    @Update("INSERT INTO busStations(name,number,route,gpsLocation ,qrCode)"
    +"VALUES (?{1},?{2},?{3},?{4},?{5})")
    public void createBusStation(String name, String number,String route,String gpsLocation,String qrCode );

    @Select("SELECT * FROM busStations WHERE qrCode = ?{1}")
    public BusStationsDo getBusStationByQrCode(String qrCode);
    
    @Update("CREATE TABLE IF NOT EXISTS busStations("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" name TEXT NOT NULL UNIQUE, "
    +" number INT NOT NULL UNIQUE, "
    +" route TEXT NOT NULL, "
    +" gpsLocation TEXT NOT NULL UNIQUE, "
    +" qrCode TEXT NOT NULL UNIQUE)")
    public void createBusStationsTable();
    
}
