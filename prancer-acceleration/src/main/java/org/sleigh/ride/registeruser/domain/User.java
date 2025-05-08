package org.sleigh.ride.registeruser.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;


    @Column(name = "email_id")
    private String emailId;


    private String password;

    private String confirmedPassword;

    @Column(name = "mobile_number")
    @Size(max = 10)
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

}