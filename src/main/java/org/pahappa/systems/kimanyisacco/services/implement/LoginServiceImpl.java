package org.pahappa.systems.kimanyisacco.services.implement;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.DAO.LoginDAO;
import org.pahappa.systems.kimanyisacco.models.Register;

public class LoginServiceImpl {
    LoginDAO loginDAO = new LoginDAO();
    Register logged_user;

    public Register allowLogin(Register user){
        String username = user.getUsername();
        String password = user.getPassword();
        
        List<Register> users_list = loginDAO.getAllUsers();

        for(Register user2:users_list){
            if(username.equals(user2.getUsername()) && BCrypt.checkpw(password, user2.getPassword())){
                logged_user =  user2;
            }else{

            }
        }

        return logged_user;
    }
}
