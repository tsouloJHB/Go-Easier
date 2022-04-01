package za.co.wethinkcode.server.api;

import io.javalin.Javalin;
import za.co.wethinkcode.server.database.DataBaseHandler;
import za.co.wethinkcode.server.domain.Domain;
import za.co.wethinkcode.server.domain.GoWallet;

import org.jetbrains.annotations.NotNull;

import static io.javalin.apibuilder.ApiBuilder.*;

public class GoServer {

    private static final int Go_PORT = 5050;
    private final Javalin app;
    //private static final int DEFAULT_PORT = Integer.parseInt(System.getenv("CLAIM_PORT"));
    private static  int DEFAULT_PORT ;
    public static DataBaseHandler dataBaseHandler;
    public static Domain domain = new Domain(dataBaseHandler);

    public  static  void main(String[] args){
        GoServer server = new GoServer();
        server.start(5050);
        System.out.println("Go Server has started");
    }

    public GoServer(){
        app =  Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });
        app.routes(() -> {
            loginAndLogoutRoutes();
            walletRoutes();
            commuteRoutes();
            // homePageRoute();
            // expenseRoutes();
            // claimRoutes();
            // settlementRoutes();
        });
        //setupRoutes(app);
        dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.dropTable();
        //System.out.println(dataBaseHandler.createUser("james", "james@gmail.com", "1234567"));
        //System.out.println(dataBaseHandler.login("james", "1234567"));
        //System.out.println(dataBaseHandler.logOut("pw8io0tefxf6fidhtep7"));
        
    }

    @NotNull
    private Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });
    }

    private void setupRoutes(Javalin server) {
        server.routes(() -> {
            loginAndLogoutRoutes();
            walletRoutes();
            commuteRoutes();
            // homePageRoute();
            // expenseRoutes();
            // claimRoutes();
            // settlementRoutes();
        });
    }

    public void start(int port) {
        app.start(port);
     }
 
     public int port() {
         return app.port();
     }
 
     public void close() {
         app.close();
     }

    public void loginAndLogoutRoutes(){
        post("/login", GoServerHandler::login);  
        get("/logout/{token}", GoServerHandler::logOut);
        post("/signup", GoServerHandler::signUp);    
    }
    public void walletRoutes(){
        get("/wallet/{token}", GoServerHandler::getWallet);  
        //get("/logout/{token}", GoServerHandler::logOut);    
    }

    public void commuteRoutes(){
        post("/commute/entry", GoServerHandler::commuteEntry);
        post("/commute/exit", GoServerHandler::commuteExit);   
    }
}
