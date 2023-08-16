package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Register;

public interface UserService {
    // public void createUser(Register user);
    public List<Register> getUsers();
    public void authenticateUser(Register user);
    public void verifyAccount(int id);
    public List<Register> getUsersToVerify();
}