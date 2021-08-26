package com.dxc.application.feature.usermaster.data.database.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {
    private BigDecimal citizenId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private BigDecimal pictureId;
    private String createdBy;
    private Date createdDt;
    private String modifiedBy;
    private Date modifiedDt;

    private String searchCitizenId;
    private String searchFirstName;
    private String searchLastName;
}
