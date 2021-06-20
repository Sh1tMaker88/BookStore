package com.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String startPage() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "index";
    }
}
