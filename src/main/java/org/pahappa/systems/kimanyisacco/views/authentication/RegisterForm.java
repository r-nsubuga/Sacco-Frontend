package org.pahappa.systems.kimanyisacco.views.authentication;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Register;
import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name="registerForm")
@SessionScoped

public class RegisterForm {
    private Register registerUser;
    UserServiceImpl userCreate = new UserServiceImpl();

    public Register getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(Register registerUser) {
        this.registerUser = registerUser;
    }

     public RegisterForm() {
        this.registerUser = new Register();
    }

    public void userRegister() throws IOException{
        userCreate.authenticateUser(registerUser);

         String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
         FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.login);
    
    }


    
}
