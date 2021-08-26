package com.dxc.application.feature.usermaster.controller;

import com.dxc.application.constants.MessagesConstants;
import com.dxc.application.exceptions.ApplicationException;
import com.dxc.application.feature.common.dto.RestJsonData;
import com.dxc.application.feature.usermaster.data.database.model.User;
import com.dxc.application.feature.usermaster.service.UserMasterService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/usermaster")
public class UserMasterController {

    private UserMasterService userMasterService;

    @Autowired
    public UserMasterController(UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
    }

    @GetMapping()
    public String initialHTML(Model model) {
        return "views/usermaster.html";
    }

    @GetMapping("/js/usermaster.js")
    public String initialJS(Model model) {
        return "js/usermaster.js";
    }

    @GetMapping("/js/usermaster-call-api.js")
    public String initialJSApi(Model model) {
        return "js/usermaster-call-api.js";
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @SneakyThrows
    RestJsonData<List<User>> getUser(@RequestBody User input, HttpServletRequest request) {
        RestJsonData<List<User>> returnData = new RestJsonData<>();
        List<User> userList = userMasterService.getUser(input);
        if (userList.isEmpty()) {
            throw new ApplicationException(MessagesConstants.ERROR_MESSAGE_DATA_NOT_FOUND, null);
        }
        returnData.setData(userList);
        return returnData;
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @SneakyThrows
    RestJsonData<String> insertUser(@RequestBody User input, HttpServletRequest request) {
        RestJsonData<String> returnData = new RestJsonData<>();
        input.setCreatedBy("csamphao");
        input.setModifiedBy("csamphao");
        try {
            int saveRowCount = userMasterService.insertUser(input);
            returnData.setRowCount(new BigDecimal(saveRowCount));
            if (saveRowCount == 0) {
                throw new ApplicationException(MessagesConstants.CONCURRENCY_ERROR, null);
            }
        }catch (DuplicateKeyException e){
            throw new ApplicationException(MessagesConstants.DATA_DUPLICATED, null);
        }
        return returnData;
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @SneakyThrows
    RestJsonData<String> updateUser(@RequestBody User input, HttpServletRequest request) {
        RestJsonData<String> returnData = new RestJsonData<>();
        input.setCreatedBy("csamphao");
        input.setModifiedBy("csamphao");
        try {
            int saveRowCount = userMasterService.updateUser(input);
            returnData.setRowCount(new BigDecimal(saveRowCount));
            if (saveRowCount == 0) {
                throw new ApplicationException(MessagesConstants.CONCURRENCY_ERROR, null);
            }
        }catch (DuplicateKeyException e){
            throw new ApplicationException(MessagesConstants.DATA_DUPLICATED, null);
        }
        return returnData;
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @SneakyThrows
    RestJsonData<String> deleteUser(@RequestBody User[] input, HttpServletRequest request) {
        RestJsonData<String> returnData = new RestJsonData<>();
        int deleteRowCount = userMasterService.deleteUser(input);
        if (deleteRowCount != input.length) {
            throw new ApplicationException(MessagesConstants.CONCURRENCY_ERROR, null);
        }
        returnData.setRowCount(new BigDecimal(deleteRowCount));
        return returnData;
    }
}
