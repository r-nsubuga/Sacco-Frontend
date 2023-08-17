package org.pahappa.systems.kimanyisacco.navigation;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Contains the links to the different pages with in the application.
 * It is to help us navigate between the pages in the application easily.
 */
@ManagedBean(name = "navigation")
@ApplicationScoped //There should be only one instance of the class created for the entire application
public class Navigation {

    private final String dashboard = "/pages/dashboard/Dashboard.xhtml";

    private final String landing = "/pages/landing/Landing.xhtml";

    private final String login = "/pages/authentications/LoginForm.xhtml";

    private final String transaction = "/pages/dashboard/deposit.xhtml";
    
    private final String account = "/pages/dashboard/account.xhtml";

    private final String profile = "/pages/dashboard/profile.xhtml";
    
    private final String services = "/pages/dashboard/services.xhtml";

    private final String adminDash = "/pages/admin/adminBoard.xhtml";

     public String getAdminDash() {
        return adminDash;
    }

    private final String adminVerify = "/pages/admin/adminVerify.xhtml";

     public String getAdminVerify() {
        return adminVerify;
    }

    private final String adminApprove = "/pages/admin/adminApprove.xhtml";

    private final String adminReport = "/pages/admin/adminReport.xhtml";

    private final String adminMembers = "/pages/admin/adminMembers.xhtml";

    private final String adminTransactions = "/pages/admin/adminTransactions.xhtml";


    public String getAdminTransactions() {
        return adminTransactions;
    }

    public String getAdminMembers() {
        return adminMembers;
    }

    public String getAdminReport() {
        return adminReport;
    }

    public String getAdminApprove() {
        return adminApprove;
    }

    public String getProfile() {
        return profile;
    }

    public String getAccount() {
        return account;
    }

    public String getTransaction() {
        return transaction;
    }

   

    public String getServices() {
        return services;
    }

    public String getLogin() {
        return login;
    }

    public String getDashboard() {
        return dashboard;
    }

    public String getLanding() {
        return landing;
    }


}
