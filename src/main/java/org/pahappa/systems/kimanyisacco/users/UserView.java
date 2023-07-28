package org.pahappa.systems.kimanyisacco.users;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Register;
//import org.pahappa.systems.kimanyisacco.models.User;
import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;

@ManagedBean(name = "userView")
@ViewScoped
public class UserView {
    private List<Register> users;
    UserServiceImpl disp_user = new UserServiceImpl();

    public List<Register> getUsers() {
        return users;
    }

    public void setUsers(List<Register> users) {
        this.users = users;
    }

    public UserView () {
        this.users = new ArrayList<>();
    }
    
    
    public void show(){
        
    }
}