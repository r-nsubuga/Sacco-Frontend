package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Member;

public interface UserService {
    // public void createUser(Member user);
    public List<Member> getUsers();
    public void authenticateUser(Member user);
    public void verifyAccount(int id);
    public List<Member> getUsersToVerify();
}