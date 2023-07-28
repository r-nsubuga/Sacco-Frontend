package org.pahappa.systems.kimanyisacco.models;

import javax.persistence.*;
import org.pahappa.systems.kimanyisacco.models.Account;

@Entity
@Table(name="members_table")
public class Register {
    private int memberId;
    private String firstName;    
    private String lastName;
    private String username;
    private String gender; 
    private String email;
    private String contact; 
    private String password; 
    private int status;
    private Account accountNumber;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getMemberId() {
        return memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Column(name="first_name", nullable=false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name", nullable=false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name="username", nullable=false, unique=true)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="gender", nullable=false)
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name="email", nullable=false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="password", nullable=false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="contact", nullable=false )
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="account_number_fk", referencedColumnName = "account_number")
    public Account getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(Account accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    @Column(name="status", columnDefinition="integer default 0")
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}