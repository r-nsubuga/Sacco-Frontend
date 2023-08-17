package org.pahappa.systems.kimanyisacco.users;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.util.ArrayList;
import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;

@ManagedBean(name = "userView")
@ViewScoped
public class UserView {
    private List<Member> members;
    UserServiceImpl disp_user = new UserServiceImpl();

    public List<Member> getUsers() {
        return members;
    }

    public void setmembers(List<Member> members) {
        this.members = members;
    }

    public UserView () {
        this.members = new ArrayList<>();
    }
    
    
    public void show(){
        
    }
}