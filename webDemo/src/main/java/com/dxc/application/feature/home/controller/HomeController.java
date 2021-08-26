<<<<<<< HEAD:webDemo/src/main/java/com/dxc/application/feature/controllers/HomeController.java
package com.dxc.application.feature.controllers;
=======
package com.dxc.application.feature.home.controller;
>>>>>>> 332efea3a07efdfe185e24fbac21e8c00a3282b4:webDemo/src/main/java/com/dxc/application/feature/home/controller/HomeController.java

import javax.servlet.http.HttpServletRequest;

import com.dxc.application.feature.common.dto.RestJsonData;
import com.dxc.application.feature.home.controller.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController {

    @GetMapping("")
    public String home(HttpServletRequest request) {
        return "views/home.html";
    }

    @GetMapping("/js/home.js")
    public String js() {
        return "js/home.js";
    }

    @GetMapping("/test")
    public @ResponseBody String test() {
        return "test";
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    RestJsonData<BigDecimal> upload(@RequestPart("userPic") MultipartFile userPic,@RequestPart("userModel") User userModel){

        return new RestJsonData<>();
    }
}
