package com.product.biodata.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BioData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String fatherName;
    private String motherName;
    private Date dob;
    private String height;
    private String weight;
    private String color;
    private String gender;
    private String religion;
    //address
    private String village;
    private String postOffice;
    private String subDistrict;
    private String district;
    private String division;

    private String phoneNumber;
    private String gmailAddress;




}
