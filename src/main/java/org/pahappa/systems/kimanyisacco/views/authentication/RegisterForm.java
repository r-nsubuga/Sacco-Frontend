package org.pahappa.systems.kimanyisacco.views.authentication;

import org.pahappa.systems.kimanyisacco.constants.Gender;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="registerForm")
@SessionScoped

public class RegisterForm {
    private Member userToRegister;
    private Gender gender;
        
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    //Getter for gender enum values
    public Gender[] getGenders() {
        return Gender.values();
    }

    public Member getUserToRegister() {
        return userToRegister;
    }

    public void setUserToRegister(Member userToRegister) {
        this.userToRegister = userToRegister;
    }

    UserServiceImpl userCreate = new UserServiceImpl();

     public RegisterForm() {
        this.userToRegister = new Member();
    }

    public void userRegister() throws IOException{
       
        userCreate.authenticateUser(userToRegister);

         String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
         FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.login);
    
    }


    
}
