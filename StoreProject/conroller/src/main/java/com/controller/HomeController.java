package com.controller;

import com.api.dao.IBookDao;
import com.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/start")
public class HomeController {

    private final IBookDao bookDao;

    @Autowired
    public HomeController(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping("/index")
    public String index(Model model) {
//        model.addAttribute("name", bookDao.getById(1L).getName());

        return "index";
    }




//    public ResponseEntity getUsers() {
//        try {
//            return ResponseEntity.ok("Server is working");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Something wrong");
//        }
//    }

}
