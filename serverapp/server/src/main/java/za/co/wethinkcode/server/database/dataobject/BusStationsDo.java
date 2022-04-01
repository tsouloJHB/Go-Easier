package za.co.wethinkcode.server.database.dataobject;

public class BusStationsDo {
    // +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
    // +" name TEXT NOT NULL, "
    // +" number TEXT NOT NULL, "
    // +" route TEXT NOT NULL, "
    // +" gpsLocation TEXT NOT NULL, "
    // +" qrToken TEXT NOT NULL, "
    // +" StationToken TEXT INT NOT NULL)")
    public int id;
    public String name;
    public int number;
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


    public int getNumber(){
        return this.number;
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
