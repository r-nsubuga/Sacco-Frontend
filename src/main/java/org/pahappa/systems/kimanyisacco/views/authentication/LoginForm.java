package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Register;
import org.pahappa.systems.kimanyisacco.services.implement.LoginServiceImpl;
import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;



@ManagedBean(name = "loginForm")
@SessionScoped

public class LoginForm {
    private Register user;
    LoginServiceImpl userlog = new LoginServiceImpl();
    List<Register> userList = new ArrayList<>();

    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Register getUser() {
        return user;
    }
    public void setUser(Register user) {
        this.user = user;
    }

    public LoginForm() {
        this.user = new Register();
    }
    
    public void init(){
        this.user = new Register();
    }
    

    public void doLogin() throws IOException{
        if(user.getUsername().equals("admin") && user.getPassword().equals("admin1234")){
            String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath(); 
            FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.adminDash);
        }

        Register loggeduser = userlog.allowLogin(user);

        if(loggeduser != null){
        if(loggeduser.getStatus() == 1){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
            HttpSession session= request.getSession(true);

            //Store user attributes 
            session.setAttribute("LoggedUser", loggeduser);

            String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath(); 
            FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.account);
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not verified", "Wait for verification"));
        } }
        else{
             FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please enter valid details"));
        }
    }
        
    
    public void logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpSession session = request.getSession(false);

        if(session!=null){
            session.invalidate();
        }

        String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.login);
        } catch (IOException e) {
            e.printStackTrace();
        }

}
   
}
    
    

