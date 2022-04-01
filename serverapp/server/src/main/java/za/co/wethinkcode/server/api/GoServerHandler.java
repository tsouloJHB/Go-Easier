package za.co.wethinkcode.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.http.Context;
import za.co.wethinkcode.server.database.dataobject.BusDo;
import za.co.wethinkcode.server.database.dataobject.BusStationsDo;
import za.co.wethinkcode.server.database.dataobject.JourneyRideDo;
import za.co.wethinkcode.server.domain.GoWallet;
import za.co.wethinkcode.server.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.eclipse.jetty.io.ssl.ALPNProcessor.Server;

import com.fasterxml.jackson.databind.DeserializationFeature;

public class GoServerHandler {


    // private static final ObjectMapper mapper = new ObjectMapper()
    // .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void login(Context context ){
        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<?, ?> map = mapper.readValue(context.body(), Map.class);

            String email = map.get("email").toString();
            String password  = map.get("password").toString();

            if(GoServer.dataBaseHandler.login(email, password)){
                User user = GoServer.dataBaseHandler.getUserByEmailPassword(email, password);
                String token =GoServer.dataBaseHandler.getToken(user.getUserId());
                viewModel.put("token", token);
                viewModel.put("username", user.getUsername());
                viewModel.put("email", user.getEmail());
            }else{
                viewModel.put("response", "false");
                context.status(401);
            }
        
        }catch(Exception ex){

        }
        context.json(viewModel);
    }

    public static void logOut(Context context ){
        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        //ObjectMapper mapper = new ObjectMapper();
        String token = context.pathParam("token");
        try{
            // Map<?, ?> map = mapper.readValue(context.body(), Map.class);

            // String token = map.get("token").toString();
            //check if token
            if(GoServer.dataBaseHandler.getLoginTokenByToken(token)){
                System.out.println("logout");
                GoServer.dataBaseHandler.logOut(token);
                viewModel.put("response", "true");
            }else{
                System.out.println("token Don't exits");
                viewModel.put("response", "false");
                context.status(401);
            }
        }catch(Exception ex){

        }
        context.json(viewModel);
    }

    public static void getWallet(Context context){
        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        String token = context.pathParam("token");

        if(GoServer.dataBaseHandler.getLoginTokenByToken(token)){
            GoWallet goWallet = GoServer.dataBaseHandler.getWallet(token);
            viewModel.put("balance", goWallet.getBalance());
        }else{
            System.out.println("token error");
            viewModel.put("response", "false");
            context.status(401);
        }
        context.json(viewModel);

    }

    public static void signUp(Context context){
        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<?, ?> map = mapper.readValue(context.body(), Map.class);

            String username = map.get("username").toString();
            String email = map.get("email").toString();
            String password  = map.get("password").toString();

            if(GoServer.dataBaseHandler.getUserByUsernamePassword(username, password) == null){
                GoServer.dataBaseHandler.createUser(username, email, password);
                // viewModel.put("token", token);
                // viewModel.put("username", user.getUsername());
                // viewModel.put("email", user.getEmail());
                viewModel.put("response", "User created");
                context.status(201);
            }else{
                viewModel.put("response", "User already exits");
                context.status(401);
            }
        
        }catch(Exception ex){

        }
        context.json(viewModel);

    }
    public static void deposit(Context context){
        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        //String token = context.formParam("token");

        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<?, ?> map = mapper.readValue(context.body(), Map.class);

            String token = map.get("token").toString();
            Double amount  = Double.parseDouble(map.get("amount").toString());
            String date  = map.get("date").toString();
            

        
        }catch(Exception ex){

        }

        context.json(viewModel);    
    }

    public static void commuteEntry(Context context){

        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        //String token = context.formParam("token");
        JourneyRideDo journeyRideDo = null;
       
        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<?, ?> map = mapper.readValue(context.body(), Map.class);
            //station or bus
            String qrCode = map.get("qrCode").toString();
            //
            //String rideType = map.get("rideType").toString();
            String token = map.get("token").toString();
            String date  = map.get("date").toString();
            String entryGpsTravel  = map.get("entryGpsCoordinates").toString();
            System.out.println();
            journeyRideDo =  GoServer.domain.createCommute(token, entryGpsTravel, qrCode, date);
            if(journeyRideDo == null){
                System.out.println("hiii");
            }
            if(journeyRideDo.enterBusStationId != 0){
                BusStationsDo busStationsDo = GoServer.dataBaseHandler.getBusStationsDo(qrCode);
                viewModel.put("type", "Bus");
                viewModel.put("BusStationId", busStationsDo.id);
                viewModel.put("Bus Number", "");
                viewModel.put("entryGpsTravel", entryGpsTravel);
                viewModel.put("Date", date);
            }else{
                BusDo bus =  GoServer.dataBaseHandler.getBusInfo(qrCode);
                viewModel.put("type", "BusStation");
                viewModel.put("BusStationId", "");
                viewModel.put("Bus Number", bus.busNumber );
                viewModel.put("entryGpsTravel", entryGpsTravel);
                viewModel.put("Date", date);

            }
          
        
        }catch(Exception ex){
            System.out.println(ex);
            context.status(400);
            viewModel.put("status", "error");
        }

        context.json(viewModel); 
    }

    public static void commuteExit(Context context){

        Map<String, Object> viewModel =  new HashMap<>(Map.of());
        //String token = context.formParam("token");

        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<?, ?> map = mapper.readValue(context.body(), Map.class);
            //station or bus
            String qrCode = map.get("qrCode").toString();
            //
            //String rideType = map.get("rideType").toString();
            String token = map.get("token").toString();
            String date  = map.get("date").toString();
            String distance  = map.get("distance").toString();
            String exitGpsTravel  = map.get("exitGpsTravel").toString();
            String exitBusStationId  = map.get("exitBusStationId").toString();

            String entryGpsTravel  = map.get("entryGpsCoordinates").toString();
            //GoServer.domain.stopCommute(token, exitGpsTravel, qrCode, 3 ,"");
            System.out.println("danger");
            JourneyRideDo journeyRideDo =  GoServer.domain.stopCommute(token, exitGpsTravel, qrCode, Integer.parseInt(exitBusStationId), distance,date);
            //JourneyRideDo journeyRideDo =  GoServer.domain.createCommute(token, entryGpsTravel, qrCode, date);
            if(journeyRideDo.enterBusStationId != 0){
                BusStationsDo busStationsDo = GoServer.dataBaseHandler.getBusStationsDo(qrCode);
                viewModel.put("type", "BusStation");
                viewModel.put("BusStationId", busStationsDo.id);
                viewModel.put("Bus Number", "");
                viewModel.put("entryGpsTravel", entryGpsTravel);
                viewModel.put("Date", date);
                viewModel.put("complete", "true");
            }else{
                BusDo bus =  GoServer.dataBaseHandler.getBusInfo(qrCode);
                viewModel.put("type", "Bus");
                viewModel.put("BusStationId", "");
                viewModel.put("Bus Number", bus.busNumber );
                viewModel.put("entryGpsTravel", entryGpsTravel);
                viewModel.put("Date", date);
                viewModel.put("complete", "true");

            }
          
        
        }catch(Exception ex){
            System.out.println(ex);
        }

        context.json(viewModel); 
    }


  
    
}
