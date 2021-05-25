package com.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    @GetMapping("/askDetails")
    public String askEmployeeDetails() {
        return "testing/askDetails-view";
    }

    @GetMapping("/showDetails")
    public String showEmpDetails(HttpServletRequest request, Model model) {
        String empName = request.getParameter("employeeName");
        empName = "Mr. " + empName;
        model.addAttribute("nameAttribute", empName);

        return "testing/showEmpDetails-view";
    }
}
