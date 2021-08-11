package com.dxc.application.controllers;

import com.dxc.application.constants.MessagesConstants;
import com.dxc.application.exceptions.ApplicationException;
import com.dxc.application.model.GimDetail;
import com.dxc.application.model.GimHeader;
import com.dxc.application.model.RestJsonData;
import com.dxc.application.services.GimMasterService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/gimmaster")
public class GIMMasterController {

    private GimMasterService gimService;

    @Autowired
    public GIMMasterController(GimMasterService gimService) {
        this.gimService = gimService;
    }

    @GetMapping()
    public String initialHTML(Model model) {
        return "views/gimmaster.html";
    }

    @GetMapping("/js/gimmaster.js")
    public String initialJS(Model model) {
        return "js/gimmaster.js";
    }

    @GetMapping("/js/gimmaster-call-api.js")
    public String initialJSApi(Model model) {
        return "js/gimmaster-call-api.js";
    }

    @PostMapping(value = "/gimheader", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @SneakyThrows
    RestJsonData<List<GimHeader>> getGimHeader(@RequestBody GimHeader input, HttpServletRequest request) {
        RestJsonData<List<GimHeader>> returnData = new RestJsonData<>();
        List<GimHeader> gimList = gimService.getGimHeader(input);
        if (gimList.isEmpty()) {
            throw new ApplicationException(MessagesConstants.ERROR_MESSAGE_DATA_NOT_FOUND, null);
        }
        returnData.setData(gimList);
        return returnData;
    }

    @PutMapping(value = "/gimheader", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    RestJsonData<String> saveGimHeader(@RequestBody GimHeader input, HttpServletRequest request) {
        RestJsonData<String> returnData = new RestJsonData<>();
        input.setCreatedBy("csamphao");
        input.setModifiedBy("csamphao");
        int saveRowCount = gimService.saveGimHeader(input);
        returnData.setRowCount(new BigDecimal(saveRowCount));
        return returnData;
    }

    @PostMapping(value = "/gimdetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    @SneakyThrows
    RestJsonData<List<GimDetail>> getGimDetail(@RequestBody GimDetail input, HttpServletRequest request) {
        RestJsonData<List<GimDetail>> returnData = new RestJsonData<>();
        List<GimDetail> gimDetailList = gimService.getGimDetail(input);
        if (gimDetailList.isEmpty()) {
            throw new ApplicationException(MessagesConstants.ERROR_MESSAGE_DATA_NOT_FOUND, null);
        }
        returnData.setData(gimDetailList);
        return returnData;
    }


    @PutMapping(value = "/gimdetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    RestJsonData<String> saveGimDetail(@RequestBody GimDetail input, HttpServletRequest request) {
        RestJsonData<String> returnData = new RestJsonData<>();
        input.setCreatedBy("csamphao");
        input.setModifiedBy("csamphao");
        int saveRowCount = gimService.saveGimDetail(input);
        returnData.setRowCount(new BigDecimal(saveRowCount));
        return returnData;
    }


    @DeleteMapping(value = "/gimdetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    RestJsonData<String> deleteGimDetail(@RequestBody GimDetail[] input, HttpServletRequest request) {
        RestJsonData<String> returnData = new RestJsonData<>();
        int deleteRowCount = gimService.deleteGimDetail(input);
        returnData.setRowCount(new BigDecimal(deleteRowCount));
        return returnData;
    }
}
