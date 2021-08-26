<<<<<<< HEAD:webDemo/src/main/java/com/dxc/application/feature/controllers/ComboController.java
package com.dxc.application.feature.controllers;
=======
package com.dxc.application.feature.common.controller;
>>>>>>> 332efea3a07efdfe185e24fbac21e8c00a3282b4:webDemo/src/main/java/com/dxc/application/feature/common/controller/ComboController.java

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
