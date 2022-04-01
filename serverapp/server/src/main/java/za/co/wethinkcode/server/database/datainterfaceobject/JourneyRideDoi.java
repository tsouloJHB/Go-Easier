package za.co.wethinkcode.server.database.datainterfaceobject;


import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.server.database.dataobject.JourneyRideDo;
import net.lemnik.eodsql.Select;

public interface JourneyRideDoi extends BaseQuery {

    @Select("SELECT * FROM journeyRide WHERE userId = ?{1} AND qrCode = ?{2} ")
    JourneyRideDo getJourneyById(int id,String qrCode);
     
    @Update("INSERT INTO journeyRide(userId,enterGpsTravel,enterBusStationId ,exitGpsTravel ,exitBusStationId,qrCode,date)"
    +"VALUES (?{1},?{2},?{3},?{4},?{5},?{6},?{7})")
    public void createJourney(int userId,String enterGpsTravel,int enterBusStationId,String exitGpsTravel,int exitBusStationId,String qrCode, String date );

    @Update("UPDATE  journeyRide SET exitGpsTravel = ?{1}, exitBusStationId = ?{2} WHERE userId = ?{3} AND qrCode = ?{4}")
    public void updateJourneyRide(int exitBusStationId,String exitGpsTravel,int userId,String qrCode);

    @Update("CREATE TABLE IF NOT EXISTS journeyRide("
    +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    +" userId INTEGER NOT NULL, "
    +" enterGpsTravel TEXT NOT NULL, "
    +" enterBusStationId INTEGER NOT NULL, "
    +" exitGpsTravel TEXT NOT NULL, "
    +" exitBusStationId INTEGER NOT NULL, "
    +" qrCode TEXT NOT NULL, "
    +" date  TEXT INT NOT NULL)")
    public void createJourneyRideTable();
    
}   
