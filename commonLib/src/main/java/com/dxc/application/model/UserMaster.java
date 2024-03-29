package com.dxc.application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class UserMaster {
    private String citizenId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String picture;
}
