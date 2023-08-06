package org.pahappa.systems.kimanyisacco.models;

import java.util.Date;

import javax.persistence.*;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.constants.Gender;

@Entity
@Table(name="members")
public class Member {    //Class for the member at registration
    private int memberId;
    private String firstName;    
    private String lastName;
    private String username;
    private Gender gender; 
    private Date date_of_birth;
    private String email;
    private String contact; 
    private String next_of_kin;
    private String next_of_kinContact;
    private String password; 
    private int status;
    private Account account;

    
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

    @Enumerated(EnumType.STRING)
    @Column(name="gender", nullable=false)
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
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

    @Column(name="member_contact", nullable=false )
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name="date_of_birth", nullable=false)
    public Date getDate_of_birth() {
        return date_of_birth;
    }
    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Column(name="next_of_kin")
    public String getNext_of_kin() {
        return next_of_kin;
    }
    public void setNext_of_kin(String next_of_kin) {
        this.next_of_kin = next_of_kin;
    }

    @Column(name="next_of_kin_contact")
    public String getNext_of_kinContact() {
        return next_of_kinContact;
    }
    public void setNext_of_kinContact(String next_of_kinContact) {
        this.next_of_kinContact = next_of_kinContact;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="account_number_fk", referencedColumnName = "account_number")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    
    @Column(name="member_status", columnDefinition="integer default 0")
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}