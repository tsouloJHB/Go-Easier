package za.co.wethinkcode.server.database.dataobject;

public class BusDo {
   
    public int id;
    public String name;
    public String busNumber;
    public String route;
    public Double gpsLocation;
    public String qrToken;
    public String stationToken;


    public int getId(){
        return this.id;
    }


    public String getName(){
        return this.name;
    }


    public String getNumber(){
        return this.busNumber;
    }

    public String getRoute(){
        return this.route;
    }
    public Double getGpsLocation(){
        return this.gpsLocation;
    }
    public String getQrToken(){
        return this.qrToken;
    }

    public String getStationToken(){
        return this.stationToken;
    }

}
