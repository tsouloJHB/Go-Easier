package za.co.wethinkcode.server.domain;

import java.time.LocalDate;
import java.util.Date;

import za.co.wethinkcode.server.api.GoServer;
import za.co.wethinkcode.server.database.DataBaseHandler;
import za.co.wethinkcode.server.database.datainterfaceobject.JourneyRideDoi;
import za.co.wethinkcode.server.database.dataobject.BusDo;
import za.co.wethinkcode.server.database.dataobject.JourneyRideDo;
import za.co.wethinkcode.server.database.dataobject.UserDo;

public class Domain {
    private DataBaseHandler dataBaseHandler;

    public Domain(DataBaseHandler dataBaseHandler){
        this.dataBaseHandler = dataBaseHandler;
    }
    public JourneyRideDo createCommute(String token,String entryGpsTravel,String qrCode,String date){
        JourneyRideDo journeyRideDo = null;
        //check
         //get user from token
        System.out.println(GoServer.dataBaseHandler.getUserByToken(token).getEmail());
         if(GoServer.dataBaseHandler.getUserByToken(token) != null){
            System.out.println("good"); 
            UserDo user = GoServer.dataBaseHandler.getUserByToken(token);
            if(GoServer.dataBaseHandler.getJourneyByIdAndQcode(user.getId(), qrCode) != null){
                System.out.println("found");
                journeyRideDo =  GoServer.dataBaseHandler.getJourneyByIdAndQcode(user.getId(), qrCode);
            }else{
                //create 
                journeyRideDo = GoServer.dataBaseHandler.createJourneyRide(user.getId(), entryGpsTravel, qrCode);
                System.out.println("create");
            }
        }
        return journeyRideDo;
    }

    public JourneyRideDo stopCommute(String token,String exitGpsTravel,String qrCode,int exitBusStationId,String distance,String date){
        JourneyRideDo journeyRideDo = null;
        //check
         //get user from token
         if(GoServer.dataBaseHandler.getUserByToken(token) != null){
            UserDo userDo = GoServer.dataBaseHandler.getUserByToken(token);
            User user = new User(userDo.getId(), userDo.getUsername(), userDo.getEmail(), userDo.getWalletById());
            if(GoServer.dataBaseHandler.getJourneyByIdAndQcode(user.getUserId(), qrCode) != null){
                System.out.println("pass1");
                journeyRideDo =  GoServer.dataBaseHandler.getJourneyByIdAndQcode(user.getUserId(), qrCode);
                //update 
                GoServer.dataBaseHandler.updateJourneyByIdAndQcode(user.getUserId() ,qrCode, exitBusStationId, exitGpsTravel);
                //make tranaction
                //check if bus entry or gps entry
                if(journeyRideDo.enterBusStationId == 0){
                    System.out.println("pass2");
                    Double amount  = calculateGpsDistanceAndAmount( Double.parseDouble(distance));
                    GoWallet wallet = GoServer.dataBaseHandler.getWallet(token);
                    wallet.withdraw(user, amount, date, token);
                    //create discription 
                    BusDo busDo = GoServer.dataBaseHandler.getBusInfo(qrCode);
                    String description = "Trip using the "+ busDo.busNumber;
                    GoServer.dataBaseHandler.createTransaction(amount, description, wallet.walletId, date, user.userId);
                }else{
                    System.out.println("pass3");
                    Double amount  = calculateStationAmount( Double.parseDouble(distance));
                    GoWallet wallet = GoServer.dataBaseHandler.getWallet(token);
                    wallet.withdraw(user, amount, date, token);
                }
                return journeyRideDo;
                //update wallet

            }
        }
        return journeyRideDo;
    }

    public Double calculateGpsDistanceAndAmount(double distance){
        return 15.0;
    }

    public Double calculateStationAmount(double distance){
        return 15.0;
    }

    public Boolean  checkIfUserExits(String token){
        System.out.println(dataBaseHandler.getUserByToken(token).getEmail());
        if( dataBaseHandler.getUserByToken(token) != null){
            
            return true;
        }
        System.out.println(token+" token");
        return true;
    }
}
