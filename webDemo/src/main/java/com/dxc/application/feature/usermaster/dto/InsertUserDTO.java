package com.dxc.application.feature.usermaster.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InsertUserDTO {
    private String citizenId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String fileName;
}
