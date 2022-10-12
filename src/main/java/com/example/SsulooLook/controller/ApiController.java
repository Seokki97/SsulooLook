package com.example.SsulooLook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ApiController {

    @GetMapping(value ="/map")
    public String apiController(){

        return "index";
    }
}
