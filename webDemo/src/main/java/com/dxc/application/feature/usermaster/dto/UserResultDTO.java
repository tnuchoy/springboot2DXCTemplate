package com.dxc.application.feature.usermaster.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserResultDTO {
    private String citizenId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String fileName;
    private BigDecimal fileId;
    private String modifiedBy;
    private Date modifiedDt;
}
