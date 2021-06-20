package com.controller;

import com.security.userService.IUserService;
import com.security.userService.MyUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

//    private final MyUserDetailsService userDetailsService;
    private final IUserService userService;

    public AdminController(
//            MyUserDetailsService userDetailsService,
            IUserService userService) {
//        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String usersList(Model model) {
        model.addAttribute("allUsers", userService.getList());
        return "/admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.getUser(userId));
        return "admin";
    }
}
