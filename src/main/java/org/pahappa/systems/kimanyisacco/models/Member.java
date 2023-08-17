package org.pahappa.systems.kimanyisacco.models;

import java.util.Date;

import javax.persistence.*;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.constants.Gender;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;

@Entity
@Table(name="members")
public class Member {    //Class for the member at registration
    private int memberId;
    private String firstName;    
    private String lastName;
    private String username;
    private Gender gender; 
    private Date dateOfBirth;
    private String email;
    private String contact; 
    private String nextOfKin;
    private String nextOfKinContact;
    private String password; 
    private MemberStatus memberStatus;
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
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name="nextOfKin")
    public String getNextOfKin() {
        return nextOfKin;
    }
    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    @Column(name="next_of_kin_contact")
    public String getNextOfKinContact() {
        return nextOfKinContact;
    }
    public void setNextOfKinContact(String nextOfKinContact) {
        this.nextOfKinContact = nextOfKinContact;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_number_fk", referencedColumnName = "account_number")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    
    @Column(name="member_status")
    public MemberStatus getMemberStatus() {
        return memberStatus;
    }
    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}