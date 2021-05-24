package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RequestMapping("/start")
public class HomeController {

    @GetMapping("/index")
    public String index() {
        return "start/index1";
    }
//    public ResponseEntity getUsers() {
//        try {
//            return ResponseEntity.ok("Server is working");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Something wrong");
//        }
//    }

}
