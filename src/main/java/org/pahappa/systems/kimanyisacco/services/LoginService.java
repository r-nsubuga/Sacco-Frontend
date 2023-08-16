package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Register;

public interface LoginService {
    boolean allowLogin(String username, String password);
    void putSession();
}
