package com.dxc.application.feature.common.controller;

import com.dxc.application.feature.common.data.database.model.Combo;
import com.dxc.application.feature.common.dto.RestJsonData;
import com.dxc.application.feature.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/combo")
public class ComboController {
    private CommonService commonService;

    @Autowired
    public ComboController(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping(value = "/gimtypecombo", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestJsonData<List<Combo>> getGimTypeCombo() {
        return new RestJsonData<List<Combo>>(null, null, commonService.getGimTypeCombo());
    }

    @GetMapping(value = "/activeflagcombo", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestJsonData<List<Combo>> getActiveFlagCombo() {
        return new RestJsonData<List<Combo>>(null, null, commonService.getActiveFlagCombo());
    }
}
