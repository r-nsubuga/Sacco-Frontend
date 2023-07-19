package org.pahappa.systems.kimanyisacco.users;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;
import org.pahappa.systems.kimanyisacco.models.User;

@ManagedBean(name = "userView")
@ViewScoped
public class UserView {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UserView () {
        this.users = new ArrayList<>();
        
        for(int i = 0; i <5; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("password" + i);

            this.users.add(user);
        }
    }
    
    public void deleteUser(User user){
        this.users.remove(user);
    }
}