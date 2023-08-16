package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.implement.LoginServiceImpl;

@ManagedBean(name = "loginForm")
@SessionScoped

public class LoginForm {
    private Member user;
    LoginServiceImpl userlog = new LoginServiceImpl();
    List<Member> userList = new ArrayList<>();
    private Member loggeduser;
    
    public Member getLoggeduser() {
        return loggeduser;
    }

    public void setLoggeduser(Member loggeduser) {
        this.loggeduser = loggeduser;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public LoginForm() {
        this.user = new Member();
    }

    public void init() {
        this.user = new Member();
    }

    String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    public void doLogin() throws IOException {
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin1234")) {
            
            FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.adminDash);
        }

        loggeduser = userlog.allowLogin(user);

        if (loggeduser != null) {
            if (loggeduser.getStatus() == 1) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
                HttpSession session = request.getSession(true);

                // Store user attributes
                session.setAttribute("LoggedUser", loggeduser);

                FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.account);
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not verified", "Wait for verification"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please enter valid details"));
        }
    }

    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        System.out.println("Session is invalidated");

        FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.login);

    }

}
