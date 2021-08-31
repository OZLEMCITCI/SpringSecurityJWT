package com.springjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/all")
    public String allAccess(){
        return "herkese acik icerir";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String userAccess(){
        return "YALNIZCA moderator INSANLAR ERISEBILIR";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String adminAccess(){
        return "ADMINE acik icerir";
    }
}
