package za.co.wethinkcode.server.database.datainterfaceobject;

import java.util.List;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.server.database.dataobject.UserDo;
import net.lemnik.eodsql.Select;

public interface  UserDoi extends BaseQuery{

    @Select("SELECT * FROM users")
    public List<UserDo> getAllUsers();


    @Select("SELECT * FROM users WHERE id = ?{1} ")
    public UserDo getUserById(int id);

    @Select("SELECT * FROM users WHERE username = ?{1} AND password = ?{2} ")
    public UserDo getUserByUsernamePassword(String username,String password);


    @Select("SELECT * FROM users WHERE email = ?{1} AND password = ?{2} ")
    public UserDo getUserByEmailPassword(String email,String password);


    @Update("INSERT INTO users(username,email,password)"
    +"VALUES (?{1},?{2},?{3})")
    public void createUser(String username,String email,String password);




    @Update("CREATE TABLE IF NOT EXISTS users("
            +" id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +" username TEXT NOT NULL, "
            +" email TEXT NOT NULL, "
            +" password TEXT NOT NULL)")
    public void createUsersTable();
}
