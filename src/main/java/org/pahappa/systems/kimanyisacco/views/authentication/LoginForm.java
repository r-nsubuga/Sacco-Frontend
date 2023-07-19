package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.User;

@ManagedBean(name = "loginForm")
@SessionScoped

public class LoginForm {
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public LoginForm() {
        this.user = new User();
    }
    public void doLogin() throws IOException{
        String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.account);
        // System.out.println(user);

        //System.out.println(base_url);
        //System.out.println(Hyperlinks.account);
    }
    
}
