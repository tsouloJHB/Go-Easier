package za.co.wethinkcode.server.database.dataobject;

public class JourneyRideDo {
   
    public int id;
    public int userId;
    public String enterGpsTravelId; 
    public int enterBusStationId; 
    public int exitGpsLocation;
    public String exitGpsTravelId ;
    public String gpsQrCode ;
    public String date;


    public int getId(){
        return this.id;
    }
    public int getUserId(){
        return this.userId;
    }

    public String getEnterGpsTravelId(){
        return this.enterGpsTravelId;
    }

    public int getEnterBusStationId(){
        return this.userId;
    }

}
