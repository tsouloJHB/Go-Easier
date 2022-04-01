package za.co.wethinkcode.server.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import groovyjarjarantlr.Token;
import javassist.bytecode.stackmap.BasicBlock.Catch;
import net.lemnik.eodsql.QueryTool;
import za.co.wethinkcode.server.database.datainterfaceobject.BusDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.BusStationsDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.GpsTravelDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.JourneyRideDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.LoginTokensDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.TransactionsDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.UserDoi;
import za.co.wethinkcode.server.database.datainterfaceobject.WalletDoi;
import za.co.wethinkcode.server.database.dataobject.BusStationsDo;
import za.co.wethinkcode.server.database.dataobject.BusDo;
import za.co.wethinkcode.server.database.dataobject.JourneyRideDo;
import za.co.wethinkcode.server.database.dataobject.LoginTokensDo;
import za.co.wethinkcode.server.database.dataobject.TransactionsDo;
import za.co.wethinkcode.server.database.dataobject.UserDo;
import za.co.wethinkcode.server.database.dataobject.WalletDo;
import za.co.wethinkcode.server.domain.GoWallet;
import za.co.wethinkcode.server.domain.User;
import za.co.wethinkcode.server.domain.wallet.Wallet;

public class DataBaseHandler {
    public static final String DISK_DB_URL = "jdbc:sqlite:";

    private static final String BusDo = null;

    private String dbUrl = DISK_DB_URL + "src/main/java/za/co/wethinkcode/server/database/bank.db";
    private Connection connection;

    public DataBaseHandler(){
        try{
            this.connection = DriverManager.getConnection(dbUrl);
             System.out.println(this.connection.toString());   
             System.out.println("database");   
            createTables();
        }catch(SQLException e){
            System.err.println( e.getMessage() );  
        }
    }

    private void createTables(){
        UserDoi userDoi = QueryTool.getQuery( connection, UserDoi.class ); 
        WalletDoi walletDoi = QueryTool.getQuery( connection, WalletDoi.class ); 
        TransactionsDoi transactionsDoi = QueryTool.getQuery( connection, TransactionsDoi.class ); 
        LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class );
        BusStationsDoi busStationsDoi = QueryTool.getQuery( connection, BusStationsDoi.class ); 
        GpsTravelDoi gpsTravelDoi = QueryTool.getQuery( connection, GpsTravelDoi.class ); 
        JourneyRideDoi journeyRideDoi = QueryTool.getQuery( connection, JourneyRideDoi.class ); 
        BusDoi busDoi = QueryTool.getQuery( connection, BusDoi.class );

        userDoi.createUsersTable();
        walletDoi.createWalletTable();
        transactionsDoi.createTransactionsTable();
        loginTokensDoi.createLoginTokensTable();
        busStationsDoi.createBusStationsTable();
        busDoi.createBusStationsTable();
        gpsTravelDoi.createGpsTravelTable();
        journeyRideDoi.createJourneyRideTable();
        createMockData();
    }

    public User getUserByUsernamePassword(String username,String password){
        UserDo userDo;
        User user = null;
        try{  
           
            final UserDoi userQuery = QueryTool.getQuery( connection, UserDoi.class );  
          
            userDo = userQuery.getUserByUsernamePassword(username, encryption(password));
            user = new User(userDo.getId(),userDo.getEmail(),userDo.getUsername(),userDo.getWalletById());
            return user;
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }   
        return user;
    }  
    public User getUserByEmailPassword(String email,String password){
        UserDo userDo;
        User user = null;
        try{  
           
            final UserDoi userQuery = QueryTool.getQuery( connection, UserDoi.class );  
          
            userDo = userQuery.getUserByEmailPassword(email, encryption(password));
            user = new User(userDo.getId(),userDo.getEmail(),userDo.getUsername(),userDo.getWalletById());
            return user;
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }   
        return user;
    }  
    

    public String createUser(String username,String email,String password){
        UserDo userDo;
        try{  
        
            final UserDoi userQuery = QueryTool.getQuery( connection, UserDoi.class ); 
            WalletDoi walletDoi = QueryTool.getQuery( connection, WalletDoi.class ); 
            //check if user exits
            
            if(userQuery.getUserByUsernamePassword(username, encryption(password)) == null){
                userQuery.createUser(username, email, encryption(password));
                //get user
                userDo = userQuery.getUserByUsernamePassword(username, encryption(password));
               
                walletDoi.createWallet(0, userDo.getId(),LocalDate.now().toString()); 
                
            }else{
                return "User already exits";    
            }
            //create user
            
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }   
        return "user created";
        
    }

    public String getToken(int userId){
        try{  
            LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
            LoginTokensDo loginTokensDo = loginTokensDoi.getTokentByUserId(userId);
            return loginTokensDo.token;
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        } 
        return "";
    }

    public Boolean getLoginTokenByToken(String token){
        try{  
           
            LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
            
            if(loginTokensDoi.getLoginTokensByToken(token) != null){
                return true;
            }else{
                return false;
            }
       
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }  
        return false;
    }
    
    public UserDo getUserByToken(String token){
        System.out.println("The token ");
        UserDo user = null;
        try{  
            
            LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
            UserDoi userQuery = QueryTool.getQuery( connection, UserDoi.class ); 
            
            if(loginTokensDoi.getLoginTokensByToken(token) != null){
                System.out.println("login tokens");
                LoginTokensDo loginTokensDo = loginTokensDoi.getLoginTokensByToken(token);
                user = userQuery.getUserById(loginTokensDo.userId);
                return user;
            }else{
                return user;   
            }
       
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
          
        }  
        return user;

    }
    public Boolean login(String email, String password){

        UserDo userDo;
       
        try{  
           
            final UserDoi userQuery = QueryTool.getQuery( connection, UserDoi.class ); 
            LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
            
            userDo = userQuery.getUserByEmailPassword(email, encryption(password));
            
            if(userDo.getId() > 0 && loginTokensDoi.getTokentByUserId(userDo.getId()) == null){
                //check if user is login in
                String token = tokenGenerator();
                System.out.println("login");
                loginTokensDoi.createToken(userDo.getId(), "true", LocalDate.now().toString(), token);

            }else{
                return false;
            }
       
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }  
        return true;
    }


    public Boolean logOut(String token){
        
  
       
        try{  
           
            
            LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
            
            if(loginTokensDoi.getLoginTokensByToken(token) != null){
                loginTokensDoi.deleteTokenById(token);

            }else{
                System.out.println("token dose not exits");
                return false;
            }
       
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }
        System.out.println("logged out");
        return true;      
    }


    public Boolean updateWallet(User user , Double amount,String date){
        try{  
  
            final WalletDoi walletDoi = QueryTool.getQuery( connection, WalletDoi.class );  
            
            if(walletDoi.getWalletById(user.getUserId()) != null){
                walletDoi.updateWallet(amount, user.getUserId());

            }else{
                System.out.println("Id dose exits");
                return false;
            }
       
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }    
        return true;
    }



    public GoWallet getWallet(String token){
        GoWallet wallet = null;
        try{  
           
           
            LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
            LoginTokensDo loginTokensDo = loginTokensDoi.getLoginTokensByToken(token);
            WalletDoi walletDoi = QueryTool.getQuery( connection, WalletDoi.class );
            WalletDo walletDo = walletDoi.getWalletByUserId(loginTokensDo.getUserId());
            wallet = new GoWallet(walletDo.getUserId(),walletDo.getBalance());
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }
         return wallet;  

    }


    //create transaction
    public  Boolean createTransaction(Double amount , String description, int walletId, String date , int userId ){
        try{  
           
            TransactionsDoi transactionsDoi = QueryTool.getQuery( connection, TransactionsDoi.class ); 
            transactionsDoi.createTransactions(amount, description, walletId, date, userId);
            return true;
            
        
         }catch(Exception e){
            //System.err.println( e.getMessage() ); 
         }
        return false; 
    }



    public void dropTable(){
        try{  
           
           
           LoginTokensDoi loginTokensDoi = QueryTool.getQuery( connection, LoginTokensDoi.class ); 
           loginTokensDoi.dropTable();
       
        }catch(Exception e){
            System.err.println( e.getMessage() ); 
        }  
    }   

    public String encryption(String password){
        String hashPassword = "";
        String saltValue = "wtc";
        hashPassword = password+saltValue;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(hashPassword.getBytes());
            BigInteger bigInt =  new BigInteger(1,messageDigest);
            hashPassword = bigInt.toString(16);
        }catch(Exception ex){

        }

        return hashPassword;
    
    }



    //create journey
    public JourneyRideDo createJourneyRide(int userId,String  entryGpsTravel,String qrCode){
        JourneyRideDo journeyRideDo = null;
        try{  
           
            BusStationsDoi busStationsDoi = QueryTool.getQuery( connection, BusStationsDoi.class ); 
            JourneyRideDoi journeyRideDoi = QueryTool.getQuery( connection, JourneyRideDoi.class ); 
            BusDoi busDoi = QueryTool.getQuery( connection, BusDoi.class );
            
            //check if qr code is for the bus station
            if(busStationsDoi.getBusStationByQrCode(qrCode) != null){
                BusStationsDo busStationsDo = busStationsDoi.getBusStationByQrCode(qrCode);
                journeyRideDoi.createJourney(userId, "0", busStationsDo.id, "0", 0, qrCode, LocalDate.now().toString());
                journeyRideDo = journeyRideDoi.getJourneyById(userId, qrCode);    
            //check if qrcode is for the bus    
            }else if(busDoi.getBusByQrCode(qrCode) != null){
                //BusDo bus = busDoi.getBusByQrCode(qrCode);
                journeyRideDoi.createJourney(userId, entryGpsTravel, 0, "0", 0, qrCode, LocalDate.now().toString());
                journeyRideDo = journeyRideDoi.getJourneyById(userId, qrCode); 
            }
            //journeyRideDoi2 = journeyRideDoi.createJourney(userId, entryGpsTravel, enterBusStationId, exitGpsTravel, exitBusStationId, qrCode, LocalDate.now().toString());
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return journeyRideDo;    
    }
    //journey code
    public JourneyRideDo getJourneyByIdAndQcode(int userId, String qrCode){
        JourneyRideDo journeyRideDoi2 = null;
        try{  
           
           
            JourneyRideDoi journeyRideDoi = QueryTool.getQuery( connection, JourneyRideDoi.class ); 

            journeyRideDoi2 = journeyRideDoi.getJourneyById(userId, qrCode);
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return journeyRideDoi2;
    }

    //update journey
    public JourneyRideDo updateJourneyByIdAndQcode(int userId, String qrCode,int exitBusStationId,String exitGpsTravel){
        JourneyRideDo journeyRideDoi2 = null;
        try{  
           
           
            JourneyRideDoi journeyRideDoi = QueryTool.getQuery( connection, JourneyRideDoi.class ); 

            journeyRideDoi.updateJourneyRide(exitBusStationId, exitGpsTravel, userId, qrCode);
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return journeyRideDoi2;
    }



    //get bus info
    public BusDo getBusInfo(String qrCode){
        BusDo bus = new BusDo();
        try{  
            BusDoi busDoi = QueryTool.getQuery( connection, BusDoi.class );
            
            //check if qr code is for the bus station
            if(busDoi.getBusByQrCode(qrCode) != null){
                bus = busDoi.getBusByQrCode(qrCode);
            }
            //journeyRideDoi2 = journeyRideDoi.createJourney(userId, entryGpsTravel, enterBusStationId, exitGpsTravel, exitBusStationId, qrCode, LocalDate.now().toString());
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return bus;   
    }

    public BusDo createBus( String busNumber,String route,String qrCode){
        BusDo bus = new BusDo();
        try{  
            BusDoi busDoi = QueryTool.getQuery( connection, BusDoi.class );
            
            busDoi.createBus("bus", busNumber, route, qrCode);
            //journeyRideDoi2 = journeyRideDoi.createJourney(userId, entryGpsTravel, enterBusStationId, exitGpsTravel, exitBusStationId, qrCode, LocalDate.now().toString());
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return bus;   

    }

    //get busStation info
      //bus code
    public BusStationsDo getBusStationsDo(String qrCode){
        BusStationsDo busStationsDo = new BusStationsDo();
        try{  
            // BusDoi busDoi = QueryTool.getQuery( connection, BusDoi.class );
            BusStationsDoi busStationsDoi = QueryTool.getQuery( connection, BusStationsDoi.class ); 
            
            //check if qr code is for the bus station
            if(busStationsDoi.getBusStationByQrCode(qrCode) != null){
                busStationsDo = busStationsDoi.getBusStationByQrCode(qrCode);
            }
            //journeyRideDoi2 = journeyRideDoi.createJourney(userId, entryGpsTravel, enterBusStationId, exitGpsTravel, exitBusStationId, qrCode, LocalDate.now().toString());
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return busStationsDo;   
    }

    public BusStationsDo createBusStation( String name,String positionNumber,String gpsLocation, String route,String qrCode){
        BusStationsDo bus = new BusStationsDo();
        try{  
            BusStationsDoi busStationsDoi = QueryTool.getQuery( connection, BusStationsDoi.class );
            
            busStationsDoi.createBusStation(name, positionNumber,gpsLocation, route, qrCode);
            //journeyRideDoi2 = journeyRideDoi.createJourney(userId, entryGpsTravel, enterBusStationId, exitGpsTravel, exitBusStationId, qrCode, LocalDate.now().toString());
        
         }catch(Exception e){
             System.err.println( e.getMessage() ); 
         }  
         return bus;   

    }


    public String tokenGenerator(){
        String token = "";
        String pool = "qwertyui2o1pa0sapdfgh9j8k7l6zx5c4v3b2n1m";
        Random rand = new Random();

        for(int x =0; x< 20;x++){
            int get =rand.nextInt(30);
           
            token += pool.charAt(get);
        }
       
        return token;
    }


    public void createMockData(){
        //check if user table has data
        List<UserDo> userDo;
        try{  
           
            final UserDoi userQuery = QueryTool.getQuery( connection, UserDoi.class ); 
           
          
            
            userDo = userQuery.getAllUsers();
            System.out.println("yes no");
            if(userDo.isEmpty()){
                //create data
                //create user
                System.out.println("yes");
                createUser("james", "james@gmail.com", "1234567");
                createUser("node", "node@gmail.com", "1234567");
                createBus("c5", "ParkTown", "hdf4545bet45bgeg4");
                createBusStation("ParkStation","4"," -26° 11' 30.00\" S | 28° 02' 18.60\" E", "Parktown", "6545bffcfg3455454");
                
               
              }
       
        }catch(Exception e){
            System.err.println( e ); 
        } 
    }
}
