package org.pahappa.systems.kimanyisacco.services.implement;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.DAO.UserDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.LoginService;

public class LoginServiceImpl implements LoginService {
    UserDAO userDAO = new UserDAO();
    Member logged_user;

    public Member allowLogin(Member user){
        String username = user.getUsername();
        String password = user.getPassword();
        
        List<Member> usersList = userDAO.getUsers();

        for(Member user2:usersList){
            if(username.equals(user2.getUsername()) && BCrypt.checkpw(password, user2.getPassword())){
                logged_user =  user2;
            }else{

            }
        }

        return logged_user;
    }
}
