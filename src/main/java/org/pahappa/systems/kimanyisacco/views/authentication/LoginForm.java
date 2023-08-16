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
import org.pahappa.systems.kimanyisacco.services.LoginService;
import org.pahappa.systems.kimanyisacco.services.implement.LoginServiceImpl;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;

@ManagedBean(name = "loginForm")
@SessionScoped

public class LoginForm {
    private Member member;
    LoginService loginService = new LoginServiceImpl();
    List<Member> userList = new ArrayList<>();
    private Member loggedMember;
    
    public Member getLoggedMember() {
        return loggedMember;
    }

    public void setLoggedMember(Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LoginForm() {
        this.member = new Member();
    }

    public void init() {
        this.member = new Member();
    }

    String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    public void doLogin() throws IOException {
        if (member.getUsername().equals("admin") && member.getPassword().equals("admin1234")) {
            
            FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.adminDash);
        }

        loggedMember = loginService.allowLogin(member);

        if (loggedMember != null) {
            if (loggedMember.getMemberStatus().equals(MemberStatus.APPROVED)) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
                HttpSession session = request.getSession(true);

                // Store member attributes
                session.setAttribute("LoggedMember", loggedMember);

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
